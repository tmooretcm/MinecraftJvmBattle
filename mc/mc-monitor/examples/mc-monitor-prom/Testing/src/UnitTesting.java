import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.*;

import com.github.dockerjava.api.model.Statistics;

class UnitTesting {
	
	static String hotspotContainerID; 
	static String openj9ContainerID;
	
	static Statistics hotspotMetrics;
	static Statistics openj9Metrics;
	
	@BeforeAll
	public static void setup() {
		try {
			PrometheusMetricsExporter.setup();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hotspotContainerID = PrometheusMetricsExporter.hotspotContainerID; 
		openj9ContainerID = PrometheusMetricsExporter.openj9ContainerID; 
		
		hotspotMetrics = PrometheusMetricsExporter.getContainerStats(hotspotContainerID).get(); 
		openj9Metrics = PrometheusMetricsExporter.getContainerStats(openj9ContainerID).get(); 
	}
	
	
	@Test 
	/**
	 * This test should acquire both the Hotspot and OpenJ9 metrics from the PrometheusMetricsExporter and compare their CPU usage. 
	 */
	void cpuUsageTest() {
		Long hotspotCPU = hotspotMetrics.getCpuStats().getSystemCpuUsage(); 
		Long openJ9CPU = openj9Metrics.getCpuStats().getSystemCpuUsage(); 
		
		assertNotNull(hotspotCPU);
		assertNotNull(openJ9CPU);
		
		System.out.println("CPU usage results:" + "\n" + "HotSpot: " + hotspotCPU + "\n" + "OpenJ9: " + openJ9CPU);	
	}
	
	@Test 
	/**
	 * This test should acquire both the Hotspot and OpenJ9 metrics from the PrometheusMetricsExporter and compare their memory usage. 
	 */
	void memoryUsageTest() {
		Long hotspotMemory = hotspotMetrics.getMemoryStats().getUsage(); 
		Long openJ9Memory = openj9Metrics.getMemoryStats().getUsage(); 
		
		assertNotNull(hotspotMemory);
		assertNotNull(openJ9Memory);
		
		System.out.println("Memory Usage Results:" + "\n" + "HotSpot: " + hotspotMemory + "\n" + "OpenJ9: " + openJ9Memory);	
	}
	
	/**
	 * More tests can be added depending on metrics that we'd like to measure. Will need to update as we go. 
	 */
	
}