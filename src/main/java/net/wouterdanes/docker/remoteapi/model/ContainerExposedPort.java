package net.wouterdanes.docker.remoteapi.model;

import org.apache.maven.plugins.annotations.Parameter;

public class ContainerExposedPort {
	
	@Parameter(required = true)
	private Integer privatePort;
	@Parameter(required = true)
	private Integer publicPort;
	@Parameter(required = false)
	private String type = "tcp";

	public Integer getPrivatePort() {
		return privatePort;
	}

	public Integer getPublicPort() {
		return publicPort;
	}

	public String getType() {
		return type;
	}

}
