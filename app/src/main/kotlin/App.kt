import elf.ElfFile
import elf.ElfFile.Companion.readInfo
import elf.program.ProgramTypes
import elf.section.SectionTypes
import elf.section.table.BaseTable.Companion.DYNSTR
import elf.section.table.BaseTable.Companion.SHSTRTAB
import elf.section.table.NoteTable
import elf.section.table.RelaTable
import elf.section.table.StringTable
import elf.section.table.SymTable
import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
//    val pathUrl = ElfFile::class.java.classLoader.getResource("libmobileffmpeg.so")
    val pathUrl = ElfFile::class.java.classLoader.getResource("libmobileffmpeg_16.so")
    val data = File(pathUrl.path).getData()
    val elfFile = ElfFile(data)
    var sectionString: StringTable? = null
    var dynString: StringTable? = null
    elfFile.apply {
        readInfo.sortedBy { it.first }.forEach {
            println((sectionStringHeader.table as StringTable).getText(it.second) + it.third)
        }
        println("==============================")
        val loadMap = mutableListOf<String>()
        programHeaders.filter { it.p_type == ProgramTypes.PT_LOAD }.forEachIndexed { index, it ->
            println("align->${it.p_align}")

            val start = it.p_offset
            val end = start + it.p_filesz
            loadMap += "[$index]\n"
            for (sectionHeader in sectionHeaders) {
                if (sectionHeader.sh_type != SectionTypes.NOBITS) {
                    val ss = sectionHeader.sh_offset
                    val se = ss + sectionHeader.sh_size
                    if (sectionHeader.sh_offset >= start && se <= end) {
                        val name = sectionHeader.name
                        loadMap += "$name\n"
                    }
                }
            }
        }
        loadMap.forEach {
            print(it)
        }
        println("[]==============================")
        sectionHeaders.forEachIndexed { index, sectionHeader ->
            val name = sectionHeader.name
            when (name) {
                DYNSTR -> dynString = sectionHeader.table as StringTable
                SHSTRTAB -> sectionString = sectionHeader.table as StringTable
            }
        }
        sectionHeaders.forEachIndexed { index, sectionHeader ->
            println("[$index] {${sectionHeader.table}}")
            when (sectionHeader.table) {
                is SymTable -> {
                    println("Num:    Value          Size Type    Bind   Vis      Ndx Name")
//                    table.symbols.forEachIndexed { index, symbol ->
//                        println("$index\t${symbol.toString(dynString)}")
//                    }
                }

                is RelaTable -> {
                    println("  Offset          Info           Type           Sym. Value    Sym. Name + Addend")
                    (sectionHeader.table as RelaTable).relas.forEachIndexed { index, rela ->
                        println("$index\t$rela")
                    }
                }

                is NoteTable -> {
                    println("note")
                    println("${(sectionHeader.table as NoteTable).note}")
                }
//                is DynamicTable->table.dynamics.forEachIndexed { index, dynamic ->
//                    println("$index\t$dynamic")
//                }
            }

        }
    }
//    println(elfFile)
}
