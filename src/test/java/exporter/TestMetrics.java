package exporter;

import static org.junit.jupiter.api.Assertions.assertNotNull; 
import static org.junit.jupiter.api.Assertions.assertTrue; 
import org.junit.jupiter.api.BeforeAll; 
import org.junit.jupiter.api.Test; 

import com.github.dockerjava.api.model.Statistics;

public class TestMetrics {
	
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
		System.out.println("\n"); 
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
		
		System.out.println("CPU Usage Results:" + "\n" + "HotSpot: " + hotspotCPU + " ns." + "\n" + "OpenJ9: " + openJ9CPU + " ns.");	
	}
	
	@Test 
	/**
	 * This test should acquire both the Hotspot and OpenJ9 metrics from the PrometheusMetricsExporter and compare their memory usage. 
	 */
	void memoryUsageTest() {
		Long hotspotMemoryUsage = hotspotMetrics.getMemoryStats().getUsage(); 
		Long hotspotMemoryLimit = hotspotMetrics.getMemoryStats().getLimit();
		Long openJ9MemoryUsage = openj9Metrics.getMemoryStats().getUsage(); 
		Long openJ9MemoryLimit = openj9Metrics.getMemoryStats().getLimit(); 	
		
		assertNotNull(hotspotMemoryUsage);
		assertNotNull(openJ9MemoryUsage);
		assertNotNull(hotspotMemoryLimit); 
		assertNotNull(openJ9MemoryLimit);

		double hotspotMemPct = ((double)hotspotMemoryUsage / hotspotMemoryLimit) * 100; 
		double openj9MemPct = ((double)openJ9MemoryUsage / openJ9MemoryLimit) * 100; 
		
		System.out.println("Memory Usage Results:" + "\n" + "HotSpot: " + hotspotMemPct + "%" + "\n" + "OpenJ9: " + openj9MemPct +"%");		
	}
	
	/**
	 * More tests can be added depending on metrics that we'd like to measure. Will need to update as we go. 
	 */
	
}