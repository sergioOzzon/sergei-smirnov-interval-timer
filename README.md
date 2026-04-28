# Sergei Smirnov Interval Timer

Тестовое Android-приложение для загрузки интервальной тренировки по ID и прохождения таймера с прогрессом, списком интервалов и звуковыми сигналами.

## Стек

- Kotlin
- Jetpack Compose + Material 3
- Navigation Compose
- Koin
- Retrofit 3 + OkHttp
- kotlinx.serialization
- SoundPool для коротких звуковых сигналов

## Архитектура

- `base/network` - Retrofit API, OkHttp client, обработка сетевых ошибок.
- `base/di` - Koin-модули приложения.
- `base/sound` - сервис звуковых сигналов тренировки.
- `data/usecases` - use case загрузки тренировки.
- `domain` - DTO модели.
- `ui/searchworkout` - экран поиска тренировки.
- `ui/workout` - экран таймера и логика выполнения тренировки.

## Возможности

- Загрузка тренировки из API по ID.
- Таймер интервалов с состояниями `IDLE`, `RUNNING`, `PAUSED`, `COMPLETED`.
- Звуковые сигналы на старт, смену интервала и завершение.
- Только портретная ориентация.
- Release build с R8 minify и resource shrinking.

## Сборка

```bash
./gradlew :app:assembleDebug
./gradlew :app:assembleRelease
```

Release сейчас подписывается debug-ключом для локальной установки на устройство.
