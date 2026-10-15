        fun inBackground(block: () -> String) {
            Thread {
                val message = try {
                    block()
                } catch (t: Throwable) {
                    "Error: " + t.javaClass.simpleName + ": " + (t.message ?: "")
                }
                runOnUiThread { show(message) }
            }.also { it.isDaemon = true; it.start() }
        }
