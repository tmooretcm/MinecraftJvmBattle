import org.junit.jupiter.api.*;

import com.github.dockerjava.api.model.Statistics;

class UnitTesting {
	
	@Test 
	/**
	 * This test should acquire both the Hotspot and OpenJ9 metrics from the PrometheusMetricsExporter and compare their CPU usage. 
	 */
	void cpuUsageTest() {
		
		String hotspotContainerID = PrometheusMetricsExporter.hotspotContainerID; 
		String openj9ContainerID = PrometheusMetricsExporter.openj9ContainerID; 
		
		Statistics hotspotMetrics = PrometheusMetricsExporter.getContainerStats(hotspotContainerID).get(); 
		Statistics openj9Metrics = PrometheusMetricsExporter.getContainerStats(openj9ContainerID).get(); 
		
		Long hotspotCPU = hotspotMetrics.getCpuStats().getSystemCpuUsage(); 
		Long openJ9CPU = openj9Metrics.getCpuStats().getSystemCpuUsage(); 
		
		System.out.println("CPU usage results:" + "\n" + "HotSpot: " + hotspotCPU + "\n" + "OpenJ9: " + openJ9CPU);	
	}
	
	@Test 
	/**
	 * This test should acquire both the Hotspot and OpenJ9 metrics from the PrometheusMetricsExporter and compare their memory usage. 
	 */
	void memoryUsageTest() {
		
		String hotspotContainerID = PrometheusMetricsExporter.hotspotContainerID; 
		String openj9ContainerID = PrometheusMetricsExporter.openj9ContainerID; 
		
		Statistics hotspotMetrics = PrometheusMetricsExporter.getContainerStats(hotspotContainerID).get(); 
		Statistics openj9Metrics = PrometheusMetricsExporter.getContainerStats(openj9ContainerID).get(); 
		
		Long hotspotMemory = hotspotMetrics.getMemoryStats().getUsage(); 
		Long openJ9Memory = openj9Metrics.getMemoryStats().getUsage(); 
		
		System.out.println("Memory Usage Results:" + "\n" + "HotSpot: " + hotspotMemory + "\n" + "OpenJ9: " + openJ9Memory);	
	}
	
	/**
	 * More tests can be added depending on metrics that we'd like to measure. Will need to update as we go. 
	 */
	
}