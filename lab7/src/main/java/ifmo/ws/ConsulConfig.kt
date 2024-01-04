package ifmo.ws

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "user")
data class ConsulConfig(val username: String)