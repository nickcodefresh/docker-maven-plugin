/*
    Copyright 2014 Wouter Danes

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

*/

package net.wouterdanes.docker.provider.model;

import java.util.*;

import net.wouterdanes.docker.remoteapi.model.ContainerLink;

/**
 * This class is responsible for holding the start configuration of a docker container<br> See <a
 * href="http://docs.docker.io/reference/api/docker_remote_api_v1.10/#21-containers">
 * http://docs.docker.io/reference/api/docker_remote_api_v1.10/#start-a-container</a>
 */
public class ContainerStartConfiguration implements Cloneable {

    public static final int DEFAULT_STARTUP_TIMEOUT = 5 * 60;

    private String image;
    private String id;
    private List<ContainerLink> links;
    private Map<String, String> env;
    
    /**
     * Regular expression to look for that indicates the container has started up
     */
    private String waitForStartup;

    /**
     * The maximum time to wait for this container to start (seconds), default is 30 sec.
     */
    private int startupTimeout;

    /**
     * Hostname to give to this container
     */
    private String hostname;

    /**
     * Whether this container should be started as "privileged" 
     */
    private boolean privileged = false;

    /**
     * Set the image name or id to use and returns a new object so you can chain from/with statements.
     *
     * @param image the image name or id
     * @return a new {@link net.wouterdanes.docker.provider.model.ContainerStartConfiguration} object with the image 
     *  name set
     */
    public ContainerStartConfiguration fromImage(String image) {
        ContainerStartConfiguration copy = copy();
        copy.image = image;
        return copy;
    }

    /**
     * Set the image id to use and returns a new object so you can chain from/with statements.
     * @param id the image id 
     * @return a new {@link net.wouterdanes.docker.provider.model.ContainerStartConfiguration} object with the image 
     *  id set
     */
    public ContainerStartConfiguration withId(String id) {
        ContainerStartConfiguration copy = copy();
        copy.id = id;
        return copy;
    }

    /**
     * Adds the passed links and returns a new object containing the links
     * @param links the links to add
     * @return a new {@link net.wouterdanes.docker.provider.model.ContainerStartConfiguration} object with the links
     *  set
     */
    public ContainerStartConfiguration withLinks(ContainerLink... links) {
        ContainerStartConfiguration copy = copy();
        if (copy.links == null) {
            copy.links = new ArrayList<>(links.length);
        }
        Collections.addAll(copy.links, links);
        return copy;
    }

    /**
     * Adds the link and returns a new object containing the link
     * @param link the links to add
     * @return a new {@link net.wouterdanes.docker.provider.model.ContainerStartConfiguration} object with the link set
     */
    public ContainerStartConfiguration withLink(ContainerLink link) {
        return withLinks(link);
    }

    /**
     * Tells the provider to wait for the container to start by checking or a regex in the docker logs  
     * @param pattern The regex to check for
     * @return a new {@link net.wouterdanes.docker.provider.model.ContainerStartConfiguration} object with the 
     *  waitForStartup set
     */
    public ContainerStartConfiguration waitForStartup(String pattern) {
        ContainerStartConfiguration copy = copy();
        copy.waitForStartup = pattern;
        return copy;
    }

    /**
     * Sets the startup timeout and returns a new object
     * @param timeout the startup timeout in seconds
     * @return a new {@link net.wouterdanes.docker.provider.model.ContainerStartConfiguration} object with the timeout set
     */
    public ContainerStartConfiguration withStartupTimeout(int timeout) {
        ContainerStartConfiguration copy = copy();
        copy.startupTimeout = timeout;
        return copy;
    }

    public ContainerStartConfiguration withEnv(Map<String, String> env) {
        ContainerStartConfiguration copy = copy();
        copy.env = env;
    	return copy;
    }

    public ContainerStartConfiguration withHostname(String hostname) {
        ContainerStartConfiguration copy = copy();
        copy.hostname = hostname;
        return copy;
    }
    
    public ContainerStartConfiguration asPrivileged() {
        ContainerStartConfiguration copy = copy();
        copy.privileged = true;
        return copy;
    }
    
    public String getImage() {
        return image;
    }

    public String getId() {
        return id != null ? id : image;
    }

    public List<ContainerLink> getLinks() {
        return links != null ? Collections.unmodifiableList(links) : Collections.<ContainerLink>emptyList();
    }

    public Map<String, String> getEnv() {
    	return env != null ? Collections.unmodifiableMap(env) : Collections.<String, String>emptyMap();
    }

    public String getHostname() {
        return hostname;
    }
    
    public String getWaitForStartup() {
        return waitForStartup;
    }

    public int getStartupTimeout() {
        return startupTimeout != 0 ? startupTimeout : DEFAULT_STARTUP_TIMEOUT;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public ContainerStartConfiguration copy() {
        try {
            return clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone went wrong, this is an internal error.", e);
        }
    }

    @Override
    protected ContainerStartConfiguration clone() throws CloneNotSupportedException {
        ContainerStartConfiguration clone = (ContainerStartConfiguration) super.clone();
        // ContainerLink is immutable, so no need to deep copy
        if (clone.links != null) {
            clone.links = new ArrayList<>(clone.links);
        }
        // Env is a Map<String, String>, so the values are immutable
        if (clone.env != null) {
            clone.env = new HashMap<>(clone.env);
        }

        return clone;
    }
}
