package com.nm.templates.launcher;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.processors.TemplateProcessor;
import com.nm.templates.processors.TemplateProcessorListener;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class AsyncLauncherListener<T extends Serializable> implements TemplateProcessorListener {
	private final T args;
	private TemplateContext context;
	private byte[] res;

	public AsyncLauncherListener(T args) {
		super();
		this.args = args;
	}

	public final void onBuildContext(TemplateContext context) {
		context.getParameters().putParameter(args);
		this.context = context;
		onStart(this.context);
	}

	public final void generate(TemplateProcessor a, OutputStream out) {
		res = ((ByteArrayOutputStream) out).toByteArray();
		onFinished(context, res);
	}

	public T getArgs() {
		return args;
	}

	public void onStart(TemplateContext context) {

	}

	public void onFinished(TemplateContext context, byte[] content) {

	}

	public byte[] getRes() {
		return res;
	}
}