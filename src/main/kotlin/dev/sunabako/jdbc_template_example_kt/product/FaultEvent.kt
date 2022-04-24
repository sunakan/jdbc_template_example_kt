package dev.sunabako.jdbc_template_example_kt.product

// Fault Tree Analysis
// https://en.wikipedia.org/wiki/Fault_tree_analysis

//
// Fault Event
//  |--Primary(木構造でいうと、葉ノード)
//  |  |--Basic: システムコンポーネントまたは要素の障害またはエラー(例：DBパスワードが間違っている、DBコネクションエラー)
//  |  |--External: 通常発生すると予想されるエラー(それ自体は障害ではない)(ビジネスロジックでのエラー)
//  |  |--Undeveloped: 十分な情報が入手できない、または重要ではないイベント
//  |  |--Conditioning: 今の所実装予定なし
//  |
//  |--Intermediate(木構造でいうと、親ノード): 中間イベント(DBError)
//

interface FaultEvent {
    interface Primary : FaultEvent {
        interface Basic : Primary
        interface External : Primary { val cause: Throwable }
        interface Undeveloped : Primary
    }

    interface Intermediate : FaultEvent {
        val cause: Throwable
        val err: FaultEvent
    }

    interface FaultEvents : FaultEvent {
        val errs: List<FaultEvent>
    }

    fun message(): String {
        return when (this) {
            is Primary.Basic, is Primary.Undeveloped -> this.javaClass.simpleName
            is Primary.External -> {
                this.javaClass.simpleName + ": " + this.cause.message
            }
            is Intermediate -> {
                this.javaClass.simpleName + ":\n" + this.err
                    .message().prependIndent(indent = "    ")
            }
            is FaultEvents -> {
                this.javaClass.simpleName + ":\n" + this.errs
                    .map { it.message().prependIndent(indent = "    ") }
                    .joinToString(separator = "\n")
            }
            else -> "$this"
        }
    }
}