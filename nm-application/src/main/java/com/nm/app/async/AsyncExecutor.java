package com.nm.app.async;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.io.FileUtils;

import com.nm.app.utils.UUIDUtils;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class AsyncExecutor<T> {
	private final AsyncExecutorContract<T> contract;
	private FutureTask<T> futureTask1;
	final String key = UUIDUtils.uuid(16);

	public AsyncExecutor(AsyncExecutorContract<T> contract) {
		super();
		this.contract = contract;
	}

	public AsyncResultDto<byte[]> getResult(String key) {
		AsyncResultDto<byte[]> result = new AsyncResultDto<byte[]>();
		result.setStatus(AsyncResultDto.Status.NotReady);
		// CHECK READY
		try {
			String b = getHelper().getText(key);
			result.setStatus(AsyncResultDto.Status.Ready);
			result.setData(FileUtils.readFileToByteArray(new File(b)));
			return result;
		} catch (NotFoundException e) {
		} catch (Exception e) {
			getHelper().saveMemory(key, e);
			result.setStatus(AsyncResultDto.Status.Failed);
			return result;
		}
		// CHECK FAILED
		try {
			int b = getHelper().getInt(key);
			if (b == -1) {
				result.setStatus(AsyncResultDto.Status.Failed);
				return result;
			}
		} catch (NotFoundException e) {
		} catch (Exception e) {
			getHelper().saveMemory(key, e);
			result.setStatus(AsyncResultDto.Status.Failed);
			return result;
		}
		return result;
	}

	public String begin() throws Exception {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		futureTask1 = new FutureTask<T>(new Callable<T>() {

			public T call() throws Exception {
				try {
					T result = contract.start();
					File file = File.createTempFile(key, "async");
					FileUtils.writeByteArrayToFile(file, contract.result());
					getHelper().saveMemory(key, file);
					return result;
				} catch (Exception e) {
					e.printStackTrace();
					getHelper().saveMemory(key, e);
					throw e;
				}
			}
		});
		executor.execute(futureTask1);
		int duration = contract.maxDuration();
		if (duration > 0) {
			executor.schedule(new Runnable() {
				public void run() {
					futureTask1.cancel(true);
				}
			}, duration, contract.maxDurationUnit());
		}
		return key;
	}

	public byte[] waitResult() throws Exception {
		futureTask1.get();
		return getResult(key).getData();
	}

	protected AsyncExecutorHelper getHelper() {
		AsyncExecutorHelper configService = ApplicationUtils.getBean(AsyncExecutorHelper.class);
		return configService;
	}
}
