package fr.simplex_software.eclipse_mp.health;

import org.eclipse.microprofile.health.*;

@Liveness
public class LivenessHealthCheck implements HealthCheck
{
  @Override
  public HealthCheckResponse call()
  {
    return HealthCheckResponse.named("Liveness health check").up().withData("key", "value").build();
  }
}
