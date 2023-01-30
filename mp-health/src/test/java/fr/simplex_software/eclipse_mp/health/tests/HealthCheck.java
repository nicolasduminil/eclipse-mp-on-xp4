package fr.simplex_software.eclipse_mp.health.tests;

import fr.simplex_software.eclipse_mp.health.*;
import org.jboss.arquillian.container.test.api.*;
import org.jboss.shrinkwrap.api.*;
import org.jboss.shrinkwrap.api.spec.*;

public class HealthCheck
{
  @Deployment(testable = false)
  public static Archive<?> deployment()
  {
    return ShrinkWrap.create(WebArchive.class,
        ReadinessHealthCheckIT.class.getSimpleName() + ".war")
      .addClasses(MpHealthApp.class);
  }
}
