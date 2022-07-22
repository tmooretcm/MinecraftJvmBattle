import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.HashMap; 

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.DockerClientBuilder;


public class PrometheusMetricsExporter {
	
	static DockerClient dockerClient;
	static HashMap<String, String> containerIDs = new HashMap<String, String>(); 
	
	/**
	 * sets up the docker
	 * @throws Exception 
	 */
	public static void setup() throws Exception {
		dockerClient = DockerClientBuilder.getInstance().build();
		Info info = dockerClient.infoCmd().exec();
        System.out.println(info);
        List<Container> containerList = dockerClient.listContainersCmd().exec();
        System.out.println(containerList);
        for(Container c : containerList) {
        	System.out.println(c.getImage() + ": " + c.getImageId());
        	if(c.getImage().contains("itzg/minecraft-server")) {
        		containerIDs.put(c.getImage(), c.getId()); 
        	}
        }
        if(containerIDs.isEmpty()) {
        	throw new Exception("Can't find minecraft server in docker");
        }
	}
	
	public static HashMap<String, String> getIDs() throws Exception {
		dockerClient = DockerClientBuilder.getInstance().build();
		Info info = dockerClient.infoCmd().exec();
        System.out.println(info);
        List<Container> containerList = dockerClient.listContainersCmd().exec();
        System.out.println(containerList);
        for(Container c : containerList) {
        	System.out.println(c.getImage() + ": " + c.getImageId());
        	if(c.getImage().equals("itzg/minecraft-server")) {
        		containerIDs.put(c.getImage(), c.getId()); 
        	}
        }
        if(containerIDs.isEmpty()) {
        	throw new Exception("Can't find minecraft server in docker");
        }
        return containerIDs; 
	}
	
	public static void main(String[] args) throws Exception {
		setup();
		
		for(Map.Entry<String, String> container: containerIDs.entrySet()) {
			Optional<Statistics> stats = getContainerStats(container.getValue());
			System.out.println(stats.get().toString());
		}
	}
	
	/**
	 * gets the stats of the specified container
	 * 
	 * @param containerId
	 * @return
	 */
	public static Optional<Statistics> getContainerStats(String containerId) {
		StatsCmd statsCmd = dockerClient.statsCmd(containerId);
		CountDownLatch countDownLatch = new CountDownLatch(1);
		StatsCallback stats = new StatsCallback(countDownLatch);
		try (StatsCallback statscallback = statsCmd.exec(stats)) {
			countDownLatch.await(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println(e);
		}
		return Optional.ofNullable(stats.getStats());
	}

	/**
	 * possible implementation of the callback object used to get docker container statistics
	 */
	private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
		private final CountDownLatch countDownLatch;
		private Boolean gotStats = false;
		private Statistics stats;
		public StatsCallback(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}
		@Override
		public void onNext(Statistics stats) {
			this.stats = stats;
			if (stats != null) {
				gotStats = true;
			}
			countDownLatch.countDown();
		}
		public Boolean gotStats() {
			return gotStats;
		}
		public Statistics getStats() {
			return stats;
		}
	}
}
