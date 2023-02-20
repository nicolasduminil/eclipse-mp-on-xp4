package fr.simplex_software.eclipse_mp.health;

import org.eclipse.microprofile.config.inject.*;
import org.eclipse.microprofile.health.*;

import javax.enterprise.context.*;
import javax.inject.*;
import java.lang.management.*;

@Liveness
@ApplicationScoped
public class LivenessHealthCheck implements HealthCheck
{
  @Inject
  @ConfigProperty(name = "freememory.threshold", defaultValue = "10485760")
  private long threshold;
  @Inject
  @ConfigProperty(name = "health.system.load.max", defaultValue = "0.75")
  private double maxLoad;

  @Override
  public HealthCheckResponse call()
  {
    HealthCheckResponseBuilder responseBuilder =
      HealthCheckResponse.named("System Uptime Health Check");
    long freeMemory = Runtime.getRuntime().freeMemory();
    if (freeMemory < threshold)
      responseBuilder.down()
        .withData("error", "Not enough free memory! Available " + freeMemory + ". Please restart application");
    else
    {
      OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
      double loadAverage = operatingSystemMXBean.getSystemLoadAverage();
      int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
      double loadAveragePerProcessor = loadAverage / availableProcessors;
      responseBuilder
        .withData("name", operatingSystemMXBean.getName())
        .withData("version", operatingSystemMXBean.getVersion())
        .withData("architecture", operatingSystemMXBean.getArch())
        .withData("processors", availableProcessors)
        .withData("loadAverage", String.valueOf(loadAverage))
        .withData("loadAverage-per-processor", String.valueOf(loadAveragePerProcessor))
        .withData("loadAverage-max", String.valueOf(maxLoad))
        .withData("freeMemory", String.valueOf(threshold));
      if (loadAveragePerProcessor < maxLoad)
        responseBuilder.up();
      else
        responseBuilder.down();
    }
    return responseBuilder.build();
  }
}
