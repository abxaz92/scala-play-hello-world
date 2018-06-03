import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z"))
