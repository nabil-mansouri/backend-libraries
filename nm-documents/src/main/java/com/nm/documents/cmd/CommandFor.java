package com.nm.documents.cmd;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.nm.app.utils.UUIDUtils;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.args.CommandChainArgumentsImpl;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandFor extends ICommand {
	public static class ForContext {
		private Map<String, CommandChainArgumentsImpl> contextById = Maps.newHashMap();
		private Map<String, Integer> indexById = Maps.newHashMap();

		public Integer getIndex(String key) {
			return indexById.get(key);
		}

		public Integer put(String key, Integer value) {
			return indexById.put(key, value);
		}

		public CommandChainArguments get(String key) {
			return contextById.get(key);
		}

		public String put(CommandChainArgumentsImpl value) {
			String key = UUIDUtils.uuid(16);
			contextById.put(key, value);
			return key;
		}

		public CommandChainArguments put(String key, CommandChainArgumentsImpl value) {
			return contextById.put(key, value);
		}

	}

	public List<CommandChainArgumentsImpl> getArgs(CommandChainArguments args) {
		String src = parsed.getFirst("for");
		return args.getFor(src);
	}

	public CommandFor(CommandParserResult parsed) {
		super(parsed);
	}

}
