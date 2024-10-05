package com.nm.templates.engines;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.nm.utils.ByteUtils;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class VelocityUtils {
	public static String run(VelocityContext context, String tag, String html) {
		StringWriter writer = new StringWriter();
		run(context, writer, tag, html);
		return writer.toString();
	}

	public static void run(VelocityContext context, OutputStream writer, String tag, String html) throws IOException {
		String value = run(context, tag, html);
		writer.write(ByteUtils.toBytes(value));
		writer.flush();
	}

	public static synchronized void run(VelocityContext context, Writer writer, String tag, String html) {
		Velocity.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
		Velocity.evaluate(context, writer, tag, html);
	}
}
