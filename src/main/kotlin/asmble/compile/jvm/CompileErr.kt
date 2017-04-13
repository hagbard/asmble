package asmble.compile.jvm

import asmble.AsmErr
import java.util.*

sealed class CompileErr(message: String, cause: Throwable? = null) : RuntimeException(message, cause), AsmErr {

    class StackMismatch(
        val expected: Array<out TypeRef>,
        val actual: TypeRef?
    ) : CompileErr("Expected any type of ${Arrays.toString(expected)}, got $actual") {
        override val asmErrString get() = "type mismatch"
    }

    class StackInjectionMismatch(
        val injectBackwardsCount: Int,
        val attemptedInsn: Insn
    ) : CompileErr("Unable to inject $attemptedInsn back $injectBackwardsCount stack values") {
        override val asmErrString get() = "type mismatch"
    }

    class BlockEndMismatch(
        val expectedStack: List<TypeRef>,
        val actualStack: List<TypeRef>
    ) : CompileErr("At block end, expected stack $expectedStack, got $actualStack") {
        override val asmErrString get() = "type mismatch"
    }

    class SelectMismatch(
        val value1: TypeRef,
        val value2: TypeRef
    ) : CompileErr("Select values $value1 and $value2 are not the same type") {
        override val asmErrString get() = "type mismatch"
    }

    class TableTargetMismatch(
        val defaultTypes: List<TypeRef>,
        val targetTypes: List<TypeRef>
    ) : CompileErr("Table jump target has types $targetTypes, but default has $defaultTypes") {
        override val asmErrString get() = "type mismatch"
    }

    class GlobalConstantMismatch(
        val index: Int,
        val expected: TypeRef,
        val actual: TypeRef
    ) : CompileErr("Global $index expected const of type $expected, got $actual") {
        override val asmErrString get() = "type mismatch"
    }

    class IfThenValueWithoutElse() : CompileErr("If has value but no else clause") {
        override val asmErrString get() = "type mismatch"
    }

    class UnusedStackOnReturn(
        val leftover: List<TypeRef>
    ) : CompileErr("Expected empty stack on return, still leftover with: $leftover") {
        override val asmErrString get() = "type mismatch"
    }

    class NoBlockAtDepth(
        val attemptedDepth: Int
    ) : CompileErr("Attempted to access block at depth $attemptedDepth, but not there") {
        override val asmErrString get() = "unknown label"
    }

    class UnknownFunc(
        val index: Int
    ) : CompileErr("Unknown function at index $index") {
        override val asmErrString get() = "unknown function"
    }

    class UnknownLocal(
        val index: Int
    ) : CompileErr("Unknown local at index $index") {
        override val asmErrString get() = "unknown local"
    }

    class UnknownGlobal(
        val index: Int
    ) : CompileErr("Unknown global at index $index") {
        override val asmErrString get() = "unknown global"
    }

    class UnknownMemory : CompileErr("No memory present") {
        override val asmErrString get() = "unknown memory"
    }

    class UnknownTable : CompileErr("No table present") {
        override val asmErrString get() = "unknown table"
    }

    class SetImmutableGlobal(
        val index: Int
    ) : CompileErr("Attempting to set global $index which is immutable") {
        override val asmErrString get() = "global is immutable"
    }

    class MutableGlobalImport(
        val index: Int
    ) : CompileErr("Attempted to import mutable global at index $index") {
        override val asmErrString get() = "mutable globals cannot be imported"
    }

    class MutableGlobalExport(
        val index: Int
    ) : CompileErr("Attempted to export global $index which is mutable") {
        override val asmErrString get() = "mutable globals cannot be exported"
    }

    class GlobalInitNotConstant(
        val index: Int
    ) : CompileErr("Expected init for global $index to be constant") {
        override val asmErrString get() = "constant expression required"
    }

    class OffsetNotConstant : CompileErr("Expected offset to be constant") {
        override val asmErrString get() = "constant expression required"
    }

    class InvalidStartFunctionType(
        val index: Int
    ) : CompileErr("Start function at $index must take no params and return nothing") {
        override val asmErrString get() = "start function"
    }
}