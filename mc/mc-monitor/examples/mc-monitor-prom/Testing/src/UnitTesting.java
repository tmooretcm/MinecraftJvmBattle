import org.junit.jupiter.api.*;
import java.util.Optional;
import com.github.dockerjava.api.model.Statistics;

class PrometheusMetricsUnitTests {
	
	/*
	 * Container ID acquisition in progress (using List<String> to grab all Minecraft server container IDs and then feeding them back in here)
	 */
	
	String hotspotContainerID = "**FILL IN HOTSPOT CONTAINER ID**";
	String openj9ContainerID = "**FILL IN OPENJ9 CONTAINER ID**";
	
	@Test 
	/**
	 * This test should acquire both the Hotspot and OpenJ9 metrics from the PrometheusMetricsExporter and compare their CPU usage. 
	 */
	void cpuUsageTest() {
		
		Optional<Statistics> hotspotMetrics = PrometheusMetricsExporter.getContainerStats(hotspotContainerID); 
		Optional<Statistics> openj9Metrics = PrometheusMetricsExporter.getContainerStats(openj9ContainerID); 
		
		Long hotspotCPU = hotspotMetrics.CpuStatsConfig().getSystemCpuUsage(); 
		Long openJ9CPU = openj9Metrics.CpuStatsConfig().getSystemCpuUsage(); 
		
		System.out.println("CPU usage results:" + "\n" + "HotSpot: " + hotspotCPU + "\n" + "OpenJ9: " + openJ9CPU);	
	}
	
	@Test 
	/**
	 * This test should acquire both the Hotspot and OpenJ9 metrics from the PrometheusMetricsExporter and compare their memory usage. 
	 */
	void memoryUsageTest() {
		
		Optional<Statistics> hotspotMetrics = PrometheusMetricsExporter.getContainerStats(hotspotContainerID); 
		Optional<Statistics> openj9Metrics = PrometheusMetricsExporter.getContainerStats(openj9ContainerID); 
		
		Long hotspotMemory = hotspotMetrics.MemoryStatsConfig().getUsage(); 
		Long openJ9Memory = openj9Metrics.MemoryStatsConfig().getUsage(); 
		
		System.out.println("Memory Usage Results:" + "\n" + "HotSpot: " + hotspotMemory + "\n" + "OpenJ9: " + openJ9Memory);	
	}
	
	/**
	 * More tests can be added depending on metrics that we'd like to measure. Will need to update as we go. 
	 */
	
}