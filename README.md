GeeksForLess Inc.

Career UP - Cluster

Завдання проекту - розробити кластер для автоматизації тестування на основі веб-драйвера Selenium. Цей кластер передбачає отримувати набір команд/скриптів від користувачів через HTTP, інтерпретує дані команди у команди/операції веб-драйвера Selenium, запускає екземпляр скрипта вебдрайвера Selenium в окремому вікні, виконує цей скрипт (набір команд). Окремим пунктом є використання індивідуального проксі-сервера для кожного сценарію.

Пропозиції щодо архітектури проекту.

Два мікросервіси, які необхідно побудувати з нуля.

Maven, Spring / Spring Boot, Selenium webdriver, Jakson Databind Multithreading/Concurrency, Git, Jakson Databind, Bash, OKhttp client, Junit, Mockito
Gradle, Spring MVC/ Spring Boot, Queue, Proxy, REST, Swagger, Heroku, Git, Junit, Mockito
Завдання першого мікросервісу - інтерпретувати команди і скрипти в операції Selenium webdriver, запустити веб-драйвер Selenium і виконати сценарії.

Завдання другого мікросервісу - управління кластерами, розбір різних джерел proxy service(файл, http source), підготовка, валідація работи proxy (оскільки джерело може передавати нефункціональні адреси проксі-сервісу) формування черги виконання скриптів в поєднанні з проксі-сервісом.

Перший етап розробки полягає в написанні ключових сервісів першого мікросервісу на основі чистої java, без використання Spring. Потім вам потрібно буде перенести цей мікросервіс на Spring. Така реалізація переслідує мету вивчення ООП і ряду шаблонів проектування, таких як: Invention of Control та Dependency injection. Також в процесі роботи над проектом будуть використані шаблони Abstract factory, Factory method, Proxy, Singleton, Listener and Publisher, Wrapper/ Decorator, Facade, Data transfer object / Builder

Перелік завдань і сервісів:

Перший мікросервіс:

Фабрика ініціалізації сервісів
WebDriverInitializerService - сервіс ініціалізації Selenium webdriver
StepExecutorService - сервіси виконання одного з кроків (пропонується реалізувати 3 різних кроки click css, click xpath, sleep
ScenarioExecutorService - сервіс виконання сценаріїв
CoreExecutorService - сервіс управління багатопотоковим виконанням
EventListenerServce - слухач нових сценаріїв для виконання
Другий мікросервіс:

Rest API для постановки нового сценарію в чергу виконання і передачі нових сценаріїв для виконання.
Web Security Filter - фільтр для запобігання несанкціонованого доступу
ProxyProcessingService сервіси для парсінгу і валідації проксі списків File / URL
EvenPublisherService - паблішер нових сценаріїв для виконання. Всі сервіси повинні бути покриті тестами.
Приклад кроку сценарію:

{ "action": "sleep", "value": "20000:25000" }

Приклад сценарію { "site": "https://test.com/center-test.html", "steps": [ { "action": "sleep", "value": "20000:25000" }, { "action": "clickcss", "value": "body > div.content > div > div.b-main_content > p:nth-child(3)" }] }

Налаштування, які повинен підтримувати перший мікросервіс: executorservice.common.driverWait = 3 executorservice.common.pageLoadTimeout = 30000 executorservice.common.threadsCount = 1 executorservice.common.webDriverExecutable = resources/chromedriver executorservice.common.userAgent = desktop

Налаштування, які повинен підтримувати другий мікросервіс: controllerservice.access.key = apikey controllerservice.common.proxySource = http://api.best-proxies.ru/proxylist.txt?key=dsfaaf&speed=1,2&type=https&level=1,2& limit=0&google=1 serviceone.common.proxySourceType = url serviceone.common.proxyType = https__
