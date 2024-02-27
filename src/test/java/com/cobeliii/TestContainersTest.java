package com.cobeliii;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestContainersTest extends AbstractTestContainers {
    @Test
    void itShouldStartPostgresDb() {
        Assertions.assertThat(postgreSQLContainer.isRunning()).isTrue();
        Assertions.assertThat(postgreSQLContainer.isCreated()).isTrue();
    }
}
