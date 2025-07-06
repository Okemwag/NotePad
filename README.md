# Notepad Android App - Clean Folder Structure

## Project Root Structure
```
NotepadApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/yourname/notepadapp/
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/
│   │   │   │   │   │   ├── NoteDatabase.kt
│   │   │   │   │   │   ├── NoteDao.kt
│   │   │   │   │   │   └── Note.kt
│   │   │   │   │   ├── remote/
│   │   │   │   │   │   ├── NoteApiService.kt
│   │   │   │   │   │   └── NoteDto.kt
│   │   │   │   │   └── NoteRepository.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── notes/
│   │   │   │   │   │   ├── NotesFragment.kt
│   │   │   │   │   │   ├── NotesViewModel.kt
│   │   │   │   │   │   └── NotesAdapter.kt
│   │   │   │   │   ├── editor/
│   │   │   │   │   │   ├── NoteEditorFragment.kt
│   │   │   │   │   │   └── NoteEditorViewModel.kt
│   │   │   │   │   └── common/
│   │   │   │   │       ├── BaseFragment.kt
│   │   │   │   │       └── BaseViewModel.kt
│   │   │   │   ├── di/
│   │   │   │   │   ├── AppModule.kt
│   │   │   │   │   └── DatabaseModule.kt
│   │   │   │   ├── utils/
│   │   │   │   │   ├── Constants.kt
│   │   │   │   │   └── Extensions.kt
│   │   │   │   └── NotepadApplication.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── fragment_notes.xml
│   │   │   │   │   ├── fragment_note_editor.xml
│   │   │   │   │   └── item_note.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── dimens.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   ├── values-night/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── ic_add.xml
│   │   │   │   │   ├── ic_note.xml
│   │   │   │   │   ├── ic_delete.xml
│   │   │   │   │   └── bg_note_item.xml
│   │   │   │   ├── anim/
│   │   │   │   │   ├── slide_in_right.xml
│   │   │   │   │   ├── slide_out_left.xml
│   │   │   │   │   ├── fade_in.xml
│   │   │   │   │   └── fade_out.xml
│   │   │   │   └── navigation/
│   │   │   │       └── nav_graph.xml
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   │   └── java/com/yourname/notepadapp/
│   │   │       ├── NoteRepositoryTest.kt
│   │   │       ├── NotesViewModelTest.kt
│   │   │       └── NoteEditorViewModelTest.kt
│   │   └── androidTest/
│   │       └── java/com/yourname/notepadapp/
│   │           ├── NoteDatabaseTest.kt
│   │           └── NotesFragmentTest.kt
│   └── build.gradle.kts
├── build.gradle.kts
├── gradle.properties
└── settings.gradle.kts
```

## Key Technologies & Libraries
- **Architecture**: MVVM with Repository pattern
- **Database**: Room (local storage)
- **Network**: Retrofit + OkHttp
- **DI**: Hilt/Dagger
- **UI**: Material Design 3 + View Binding
- **Navigation**: Navigation Component
- **Testing**: JUnit, Mockito, Espresso
- **Animations**: Lottie + Material Motion

## Core Features
1. **Notes Management** - Create, edit, delete, search notes
2. **Rich Text Editor** - Formatting, colors, sizes
3. **Categories/Tags** - Organize notes
4. **Cloud Sync** - Backup to Golang backend
5. **Dark/Light Theme** - Beautiful UI with animations
6. **Search & Filter** - Quick note discovery

## Beautiful UI Elements
- Material Design 3 components
- Smooth page transitions
- FAB animations
- Swipe gestures
- Shimmer loading effects
- Custom ripple effects
- Gradient backgrounds