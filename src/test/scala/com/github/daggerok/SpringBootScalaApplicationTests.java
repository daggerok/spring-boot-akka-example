package com.github.daggerok;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/*
// Junit 4:
import org.junit.Test;

public class SpringBootScalaApplicationTests {

  @Test
  public void main() {
    GenericApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBootScalaApplication.class);
    assertThat(ctx).isNotNull();
    SpringBootScalaApplication.main(new String[0]);
  }
}
*/

// Junit 5 (Jupiter):
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Junit 5 Test")
class SpringBootScalaApplicationTests {

  @Test
  void main() {
    GenericApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBootScalaApplication.class);
    assertThat(ctx).isNotNull();
    SpringBootScalaApplication.main(new String[0]);
  }
}
