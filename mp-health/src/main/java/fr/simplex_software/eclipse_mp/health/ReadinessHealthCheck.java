package fr.simplex_software.eclipse_mp.health;

import org.eclipse.microprofile.health.*;

@Readiness
public class ReadinessHealthCheck implements HealthCheck
{
  @Override
  public HealthCheckResponse call()
  {
    return HealthCheckResponse.named("Readiness health check").up().withData("key", "value").build();
  }
}
