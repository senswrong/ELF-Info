import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream

fun File.getData(): ByteArray = BufferedInputStream(FileInputStream(this)).use {
    ByteArray(it.available()).also { d ->
        it.read(d)
    }
}