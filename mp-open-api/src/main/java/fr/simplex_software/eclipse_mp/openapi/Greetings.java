package fr.simplex_software.eclipse_mp.openapi;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Greetings
{
  private String greetings;

  public Greetings()
  {
  }

  public Greetings(String greetings)
  {
    this.greetings = greetings;
  }

  public String getGreetings()
  {
    return greetings;
  }

  public void setGreetings(String greetings)
  {
    this.greetings = greetings;
  }
}
