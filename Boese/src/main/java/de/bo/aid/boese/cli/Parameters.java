package de.bo.aid.boese.cli;

import com.beust.jcommander.Parameter;

public class Parameters {
	
	/**  Describes the path to the config file. */
	@Parameter(names = "-config", description = "Path to the config-file", required=true)
	private String config;


	/**  If set a defalt config is genrated. */
	@Parameter(names = "-genconfig", description = "Generates a default config file at the location configured with -config")
	private boolean genConfig = false;

	
	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public boolean isGenConfig() {
		return genConfig;
	}

	public void setGenConfig(boolean genConfig) {
		this.genConfig = genConfig;
	}
}
