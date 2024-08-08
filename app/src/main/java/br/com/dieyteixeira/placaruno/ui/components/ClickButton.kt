package br.com.dieyteixeira.placaruno.ui.components

class ClickHandler {
    private var lastClickTime = 0L

    fun canClick(): Boolean {
        val currentTime = System.currentTimeMillis()
        return if (currentTime - lastClickTime > 1000) { // Verifica se passou 1 segundo
            lastClickTime = currentTime
            true
        } else {
            false
        }
    }
}