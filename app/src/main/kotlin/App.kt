import elf.ElfFile
import elf.ElfFile.Companion.readInfo
import elf.program.ProgramTypes
import elf.section.SectionTypes
import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
//    val pathUrl = ElfFile::class.java.classLoader.getResource("libmobileffmpeg.so")
    val pathUrl = ElfFile::class.java.classLoader.getResource("libmobileffmpeg_16.so")
    val data = File(pathUrl.path).getData()
    val elfFile = ElfFile(data)
    elfFile.apply {
        readInfo.sortedBy { it.first }.forEach {
            println(stringTable.getText(it.second) + it.third)
        }
        println("==============================")
        val loadMap = mutableListOf<String>()
        programHeaders.filter { it.p_type == ProgramTypes.PT_LOAD }.forEachIndexed { index, it ->
            val start = it.p_offset
            val end = start + it.p_filesz
            loadMap += "[$index]\n"
            for (sectionHeader in sectionHeaders) {
                if (sectionHeader.sh_type != SectionTypes.SHT_NOBITS) {
                    val ss = sectionHeader.sh_offset
                    val se = ss + sectionHeader.sh_size
                    if (sectionHeader.sh_offset >= start && se <= end) {
                        val name = stringTable.getText(sectionHeader.sh_name)
                        loadMap += "$name\n"
                    }
                }
            }
        }
        loadMap.forEach {
            print(it)
        }
    }
//    println(elfFile)
}
