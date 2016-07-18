package org.iqu.crawler.configuration;

public class SourceConfiguration {

	private String parserClassName;
	private String source;

	public SourceConfiguration(String parserName, String source) {
		this.parserClassName = parserName;
		this.source = source;
	}

	public String getParserName() {
		return parserClassName;
	}

	public String getSource() {
		return source;
	}

	@Override
	public String toString() {
		return "SourceConfiguration [parserName=" + parserClassName + ", source=" + source + "]";
	}
	
	
}
