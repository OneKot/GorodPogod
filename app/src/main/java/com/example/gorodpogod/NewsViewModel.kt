package com.example.gorodpogod

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {
    var selectedNews by mutableStateOf<News?>(null)
    val newsList = mutableStateListOf(
        News(1, "Культура",
            "Тайные улочки Барселоны",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud e...",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                    " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                    " Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                    "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. " +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                    " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                    " Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
            R.drawable.news1,
            "5 минут назад",
            "Время чтения: 15 мин"),

        News(2, "Технологии",
            "Как проходит рабочий день PM в IT-компании",
            "Рабочий день РМ (Project Manager) в IT-компании начинается обычно с утреннего планирования. Первым делом он проверяет почту, мессен...",
            "Рабочий день РМ (Project Manager) в IT-компании начинается обычно с утреннего планирования." +
                    " Первым делом он проверяет почту, мессенджеры и задачи в трекере, чтобы понимать статус проектов и приоритеты на день." +
                    " Далее проходят ежедневные стендапы с командами разработки, где обсуждаются выполненные задачи, текущие трудности и план на день." +
                    " РМ помогает распределять ресурсы, решает блокирующие вопросы и согласовывает сроки с командой и заказчиком." +
                    " В течение дня РМ проводит встречи с клиентами и внутренними отделами, отслеживает прогресс задач, обновляет дорожные карты и отчёты по проекту." +
                    " Также он анализирует риски, ищет решения проблем и мотивирует команду для поддержания продуктивной работы." +
                    " В конце дня РМ обычно подводит итоги: проверяет, что выполнено, какие задачи переносятся на завтра," +
                    " и обновляет статусы в системах управления проектами. Важная часть работы — поддерживать баланс между сроками," +
                    " качеством продукта и комфортом команды.Такой ритм позволяет РМ эффективно управлять проектами и обеспечивать успешную работу команды," +
                    " несмотря на динамичную и порой непредсказуемую среду IT-компании.",
            R.drawable.news2,
            "3 часа назад",
            "Время чтения: 25 мин"),

        News(3, "Технологии",
            "Как проходит рабочий день Сеньера",
            "День Senior PM начинается с проверки почты, сообщений и задач в трекере. Он оценивает приоритеты, чтобы понять, что критично, а что можно отложить. Утрен...",
            "День Senior PM начинается с проверки почты, сообщений и задач в трекере. " +
                    "Он оценивает приоритеты, чтобы понять, что критично, а что можно отложить. " +
                    "Утренний блок обычно начинается со стендапа с командой: обсуждаются задачи," +
                    " достижения и блокеры. Senior PM помогает решать проблемы, согласовывает приоритеты и следит," +
                    " чтобы команда работала продуктивно. Дальше идут встречи с клиентами и руководством: согласование сроков, " +
                    "требований и ресурсов, обсуждение рисков и подготовка решений. В это время Senior PM фиксирует все договорённости и готовит отчёты." +
                    " После встреч он анализирует прогресс задач, обновляет дорожные карты и следит за качеством работы команды. " +
                    "Обед часто короткий — иногда за рабочим столом с просмотром писем. Во второй половине дня снова встречи и менторство для младших коллег." +
                    " Вечером подводятся итоги: что выполнено, что остаётся, расставляются приоритеты на завтра. " +
                    "Хотя день насыщен, Senior PM поддерживает баланс между сроками, качеством и комфортом команды. " +
                    "От его решений зависит успешность проекта и удовлетворённость клиентов.",
            R.drawable.news3,
            "Вчера",
            "Время чтения: 20 мин")
    )
}