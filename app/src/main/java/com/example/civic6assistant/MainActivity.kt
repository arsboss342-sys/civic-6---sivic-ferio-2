package com.example.civic6assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CivicAssistantApp()
                }
            }
        }
    }
}

data class CivicTopic(
    val category: String,
    val title: String,
    val keywords: List<String>,
    val answer: String
)

data class ChatItem(
    val text: String,
    val isUser: Boolean
)

object Civic6OfflineDatabase {
    val topics = listOf(
        CivicTopic(
            category = "Кузова и поколения",
            title = "Общая справка по Honda Civic 6 (1995–2000)",
            keywords = listOf("кузов", "ej9", "ek3", "ek4", "ek9", "ej6", "ej8", "em1", "рест", "дорест", "поколение"),
            answer = """
                Honda Civic 6 поколения выпускался с 1995 по 2000 год:
                • Хэтчбеки: EJ9 (1.4), EK3 (1.5 VTEC-E), EK4 (1.6 VTi B16A2), EK9 (Type R B16B).
                • Седаны: EJ9, EK3, EK4, Ferio (JDM).
                • Купе: EJ6 (D16Y7), EJ8 (D16Y8), EM1 (Si B16A2).
                • Рестайлинг (1999–2000): другая передняя оптика, решётка радиатора, передний и задний бамперы, крылья и капот. По технике и подвеске 96–98 и 99–00 в основном идентичны.
            """.trimIndent()
        ),
        CivicTopic(
            category = "Двигатели D-серии",
            title = "Зазоры клапанов D14 / D15 / D16 (на холодную)",
            keywords = listOf("клапана", "зазор", "зазоры", "регулировка", "d14", "d14a3", "d14a4", "d15z6", "d16y7", "d16y8"),
            answer = """
                Регулировка клапанов на D-серии (D14A3/A4, D15Z6, D16Y7/Y8) делается строго на холодном моторе (температура ГБЦ ниже 38°C):
                • Впускные клапаны: 0.18 – 0.22 мм (оптимально 0.20 мм).
                • Выпускные клапаны: 0.23 – 0.27 мм (оптимально 0.25 мм).
                • Порядок работы цилиндров: 1 - 3 - 4 - 2 (счёт от шкива ГРМ).
                • Момент затяжки контргаек регулировочных винтов: 20 Н·м (для D14/D15/D16).
            """.trimIndent()
        ),
        CivicTopic(
            category = "Двигатели B-серии",
            title = "Зазоры клапанов B16A2 / B16B (DOHC VTEC)",
            keywords = listOf("b16", "b16a2", "b16b", "клапана", "зазоры", "vtec", "ek4", "ek9", "em1"),
            answer = """
                Зазоры клапанов для B16A2 и B16B (на холодную, ниже 38°C):
                • Впускные клапаны: 0.15 – 0.19 мм (оптимально 0.17 мм).
                • Выпускные клапаны: 0.17 – 0.21 мм (оптимально 0.19 мм).
                • Свечи зажигания: NGK BKR6E-11 (сток) или BKR7E-11 (для активной езды / Type R).
            """.trimIndent()
        ),
        CivicTopic(
            category = "Моменты затяжки",
            title = "Основные моменты затяжки Civic 6",
            keywords = listOf("момент", "затяжка", "затяжки", "гбц", "колеса", "свечи", "сливная", "пробка", "грм"),
            answer = """
                Ключевые моменты затяжки для Civic 6 (D-серия):
                • Болты ГБЦ: 1-й проход 29 Н·м, 2-й проход 67 Н·м (порядок от центра крест-накрест).
                • Клапанная крышка: 9.8 Н·м (не перетягивать — легко сорвать шпильки!).
                • Сливная пробка поддона двигателя: 44 Н·м (шайба 14 мм).
                • Сливная/заливная пробка МКПП (S40): 44 Н·м / 44 Н·м.
                • Свечи зажигания: 18 Н·м.
                • Колёсные гайки: 108 Н·м.
                • Болт шкива коленвала: 181 Н·м (на D-серии).
            """.trimIndent()
        ),
        CivicTopic(
            category = "Самодиагностика",
            title = "Коды ошибок Check Engine (диагностика скрепкой)",
            keywords = listOf("ошибка", "ошибки", "чек", "check", "скрепка", "разъем", "диагностика", "мигает", "код"),
            answer = """
                Как считать ошибки без сканера:
                1. Выключи зажигание. Справа под бардачком (у правой ноги пассажира) найди синий 2-пиновый разъём (обычно в зелёном резиновом чехле).
                2. Перемкни оба контакта скрепкой и включи зажигание (мотор не заводить).
                3. Считай мигания лампы Check Engine: длинное = 10, короткое = 1.
                Частые коды:
                • 1 — Лямбда-зонд (O2 sensor)
                • 3 / 5 — Датчик MAP (разряжения во впуске)
                • 6 — Датчик температуры ОЖ (ECT)
                • 7 — Датчик положения дросселя (TPS)
                • 8 / 9 — Датчики ВМТ / положения цилиндра в трамблёре (TDC / CYP)
                • 10 — Датчик температуры воздуха на впуске (IAT)
                • 14 — Клапан холостого хода (IACV)
                • 17 — Датчик скорости (VSS)
                • 21 / 22 — Клапан / датчик давления VTEC
                • 41 — Подогрев лямбда-зонда
            """.trimIndent()
        ),
        CivicTopic(
            category = "Типовые болячки",
            title = "Плавают обороты ХХ / не заводится на горячую",
            keywords = listOf("обороты", "плавают", "холостой", "iacv", "заводится", "горячую", "реле", "main", "трамблер"),
            answer = """
                Две главные классические болячки Civic 6:
                1. Плавают обороты (1000–2000 об/мин вверх-вниз):
                   • Завоздушена система охлаждения (проверь уровень антифриза в радиаторе на холодную и выгони воздух).
                   • Забита сетка клапана холостого хода (IACV) на дросселе/впуске — снять, промыть карбклинером.
                   • Подсос воздуха через прокладку впуска или дросселя.
                2. На холодную заводится с пол-оборота, на горячую крутит, но не схватывает:
                   • Микротрещины пайки в Main Relay (главное реле бензонасоса, стоит справа за бардачком). Лечится пропайкой контактов на плате реле за 15 минут.
            """.trimIndent()
        ),
        CivicTopic(
            category = "Жидкости и объёмы",
            title = "Масла, антифриз и техжидкости Civic 6",
            keywords = listOf("масло", "объем", "сколько", "лить", "антифриз", "мкпп", "акпп", "гур", "тормозная"),
            answer = """
                Заправочные объёмы Honda Civic 6:
                • Масло в двигатель (с фильтром): D14/D15/D16 — 3.3–3.6 л; B16A2 — 4.0 л (вязкость 5W-30 / 5W-40).
                • Масло в МКПП (S40 / S4C): 1.8 – 1.9 л. Лить ТОЛЬКО Honda MTF (в крайнем случае временно 10W-30/10W-40, но не обычную трансмиссионку 75W-90!).
                • Жидкость ГУР: 1.0 л — ТОЛЬКО Honda PSF (обычный Dextron убьёт рейку и насос).
                • Антифриз: ~4.5 л (G11/G12+ без силикатов, в идеале Honda Type 2).
                • Тормозная жидкость: DOT-3 / DOT-4 (~0.7 л на полную прокачку).
            """.trimIndent()
        ),
        CivicTopic(
            category = "Тюнинг и свап",
            title = "Тормоза и подвеска: что встаёт болт-он (262/282, стабы)",
            keywords = listOf("тормоза", "262", "282", "свап", "стабилизатор", "стаб", "подвеска", "рычаги", "дисковые", "зтд"),
            answer = """
                Популярные доработки Civic 6 (EJ9 / EK3):
                • Передние тормоза: на EJ9 часто стоят 240 мм. Чтобы перейти на 262 мм (от EK4/EK3 VTi), нужны поворотные кулаки под 262 мм, скобы и суппорты. На кулаки 262 мм уже болт-он встают суппорты и диски 282 мм от Prelude / Legend / Accord Type R (с дисками 4x100 от MG ZR 160).
                • Задние дисковые тормоза (ЗТД): встают в сборе с продольными рычагами от EK4, Integra DC2 или Civic MB6 (у MB6 разболтовка 4x114.3, у EK4 — 4x100).
                • Стабилизаторы: передний 26 мм от EK4 встаёт с передними нижними рычагами от EK4/EK3. Задний стаб от EK4 (15 мм) или EK9 (22 мм) — с усилителем подрамника (ASR/Beaks), чтобы не вырвало металл!
            """.trimIndent()
        )
    )

    fun findAnswer(query: String): String {
        val clean = query.lowercase().trim()
        if (clean.isEmpty()) return "Напиши вопрос по Civic 6 — например, про клапана, ошибки или масло."

        val words = clean.split(Regex("[^a-zа-яё0-9]+")).filter { it.length >= 2 }

        val ranked = topics.map { topic ->
            var score = 0
            val fullText = "${topic.category} ${topic.title} ${topic.answer}".lowercase()
            for (word in words) {
                if (topic.keywords.any { it.contains(word) || word.contains(it) }) score += 4
                if (topic.title.lowercase().contains(word)) score += 3
                if (fullText.contains(word)) score += 1
            }
            topic to score
        }.filter { it.second > 0 }
            .sortedByDescending { it.second }

        return if (ranked.isEmpty()) {
            "По этому запросу в локальной базе пока нет точной карточки. Попробуй спросить про:\n" +
                    "• зазоры клапанов (D14/D15/D16 или B16)\n" +
                    "• моменты затяжки\n" +
                    "• коды ошибок (диагностика скрепкой)\n" +
                    "• плавающие обороты или Main Relay\n" +
                    "• объёмы масел и жидкостей\n" +
                    "• свап тормозов 262/282 и стабилизаторов"
        } else {
            val best = ranked.first().first
            val extra = if (ranked.size > 1 && ranked[1].second >= 4) {
                "\n\n📌 Также по теме: «${ranked[1].first.title}»"
            } else ""
            "🔧 ${best.title}\n\n${best.answer}$extra"
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CivicAssistantApp() {
    var input by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatItem(
                text = "Здарова, бро! Я твой офлайн-помощник по Honda Civic 6 поколения (EJ/EK/EM1, 1995–2000). Интернет не нужен — спрашивай про зазоры, моменты затяжки, ошибки или свап.",
                isUser = false
            )
        )
    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val quickChips = listOf(
        "Зазоры клапанов D15Z6",
        "Ошибки Check скрепкой",
        "Моменты затяжки",
        "Сколько масла в МКПП",
        "Плавают обороты",
        "Тормоза 262 / 282"
    )

    fun askQuestion(question: String) {
        val trimmed = question.trim()
        if (trimmed.isEmpty()) return
        messages.add(ChatItem(trimmed, isUser = true))
        val reply = Civic6OfflineDatabase.findAnswer(trimmed)
        messages.add(ChatItem(reply, isUser = false))
        coroutineScope.launch {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Civic 6 Garage AI", fontWeight = FontWeight.Bold)
                        Text(
                            "Offline • EJ / EK / EM1 (1995–2000)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { msg ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (msg.isUser) Arrangement.End else Arrangement.Start
                    ) {
                        Surface(
                            color = if (msg.isUser) {
                                MaterialTheme.colorScheme.primaryContainer
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.widthIn(max = 330.dp)
                        ) {
                            Text(
                                text = msg.text,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                quickChips.forEach { chipText ->
                    SuggestionChip(
                        onClick = { askQuestion(chipText) },
                        label = { Text(chipText) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Спроси про Civic 6...") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        askQuestion(input)
                        input = ""
                    }
                ) {
                    Text("Спросить")
                }
            }
        }
    }
}
