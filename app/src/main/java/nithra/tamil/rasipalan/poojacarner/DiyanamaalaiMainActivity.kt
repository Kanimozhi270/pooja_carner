/*
package com.nithra.diyanamalai

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.io.File

class DiyanamaalaiMainActivity : ComponentActivity() {
    lateinit var customFontFamily: FontFamily
    var preference = DiyanamaaliaiSharedPreference()
    lateinit var selectedSoundNew: MutableState<Int?>
    lateinit var backGroundselectedPosition: MutableState<Int>
    lateinit var maalaiSelectedPosition: MutableState<Int>
    lateinit var maalaiThreadSeletedPos: MutableState<Int>
    lateinit var dropCount: MutableState<Int>
    lateinit var reachedGoal: MutableState<Boolean>
    lateinit var countField: MutableState<String>
    lateinit var lazyListState: LazyListState
    lateinit var currentMediaPlayer: MutableState<MediaPlayer?>
    lateinit var currentlyPlaying: MutableState<Int?>
    lateinit var isPlaying: MutableState<Boolean>
    lateinit var isSelected: MutableState<Boolean>
    lateinit var isPaused: MutableState<Boolean>
    lateinit var isShowBottom: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            selectedSoundNew = remember { mutableStateOf<Int?>(null) }
            backGroundselectedPosition = remember { mutableStateOf<Int>(0) }
            maalaiSelectedPosition = remember { mutableStateOf<Int>(0) }
            countField = remember { mutableStateOf("11") }
            dropCount = remember { mutableStateOf(0) }
            maalaiThreadSeletedPos = remember { mutableStateOf(0) }
            reachedGoal = remember { mutableStateOf(false) }
            lazyListState = rememberLazyListState()
            currentMediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }
            currentlyPlaying = remember { mutableStateOf<Int?>(R.raw.diayanamaalai_om_sound) }
            isSelected = remember { mutableStateOf<Boolean>(false) }
            isPlaying = remember { mutableStateOf(false) }
            isPaused = remember { mutableStateOf(false) }
            isShowBottom = remember { mutableStateOf(false) }

            preference.putInt(
                this@DiyanamaalaiMainActivity,
                "Click_Count",
                countField.value.toInt()
            )
            customFontFamily = FontFamily(
                Font(R.font.lexend_black, FontWeight.Bold),
                Font(R.font.lexend_medium, FontWeight.Normal)
            )
            Box(modifier = Modifier.fillMaxSize()) {
                var showShowcase by remember { mutableStateOf(true) }

                // Example targets
                val targets = listOf(
                    ShowcaseTarget(Offset(100f, 200f), 50f, "This is your button."),
                    ShowcaseTarget(Offset(200f, 400f), 70f, "This is your LazyColumn item.")
                )
                ItemByItemScrollLazyColumn(this@DiyanamaalaiMainActivity)
                ShowcaseView(
                    isVisible = showShowcase,
                    targets = targets,
                    onDismiss = { showShowcase = false }
                )

            }

        }
    }
    // Data class for target elements
    data class ShowcaseTarget(
        val position: Offset,
        val radius: Float,
        val description: String
    )
    @Composable
    fun ShowcaseView(
        isVisible: Boolean,
        targets: List<ShowcaseTarget>,
        onDismiss: () -> Unit
    ) {
        println("isvisible == $isVisible")
        if (isVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .pointerInput(Unit) {} // Block touch interactions on the dimmed background
            ) {
                for (target in targets) {
                    // Highlight Area
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = Color.Transparent,
                            center = target.position,
                            radius = target.radius,
                            blendMode = BlendMode.Clear
                        )
                    }

                    // Tooltip
                    Column(
                        modifier = Modifier
                            .offset(target.position.x.dp, target.position.y.dp)
                            .padding(16.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            text = target.description,
                            style = TextStyle(color = Color.Black, fontSize = 14.sp)
                        )
                    }
                }

                // Close Button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .clickable { onDismiss() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.diyanamalai_settings),
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }
        }
    }

    @Composable
    fun ItemByItemScrollLazyColumn(context: Context) {

        // Shake animation state
        val offsetX = remember { Animatable(0f) }
        // Remember CoroutineScope for launching coroutines
        val coroutineScope = rememberCoroutineScope()


        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    return if (delta > 0) {
                        Offset.Zero
                    } else {
                        Offset(available.x, available.y)
                    }
                }
            }
        }
        // Gesture detection modifier
        val swipeGestureModifier = Modifier
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { _, dragAmount ->
                        if (dragAmount > 0) { // Detect only downward swipe gestures
                            coroutineScope.launch {
                                // Vibrate on gesture detection
                                val vibrator =
                                    context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    vibrator.vibrate(
                                        VibrationEffect.createOneShot(
                                            50,
                                            VibrationEffect.DEFAULT_AMPLITUDE
                                        )
                                    )
                                } else {
                                    vibrator.vibrate(2050) // Deprecated in API 26+
                                }

                                // Trigger the shake animation
                                offsetX.animateTo(
                                    targetValue = 20f, // Shake to the right
                                    animationSpec = tween(durationMillis = 50)
                                )
                                offsetX.animateTo(
                                    targetValue = -20f, // Shake to the left
                                    animationSpec = tween(durationMillis = 50)
                                )

                                val nextIndex = lazyListState.firstVisibleItemIndex + 1
                                println("nextIndex == $nextIndex")
                                if (nextIndex >= (countField.value.toInt())) {
                                    coroutineScope.launch {
                                        lazyListState.scrollToItem(0) // Reset scroll position to the top
                                        dropCount.value = 0         // Reset scroll count
                                        reachedGoal.value = true
                                    }
                                }
                                if (nextIndex < 10000) {
                                    lazyListState.animateScrollToItem(nextIndex)
                                }

                            }
                        }
                    },
                    onDragEnd = {}
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { */
/* Consume the long press and do nothing *//*
 },
                    onPress = { */
/* Consume the press and prevent unintended triggers *//*
 }
                )
            }
        if (reachedGoal.value) {

            ShowCongratsDialog(
                onDismiss = {
                    reachedGoal.value = false
                }, reachedGoal.value,
                onPlay = { lastplayedMusic ->

                    println("lastPlay == $lastplayedMusic")
                    if (lastplayedMusic != 0) {
                        currentMediaPlayer.value = MediaPlayer.create(
                            this@DiyanamaalaiMainActivity,
                            lastplayedMusic
                        )
                        currentMediaPlayer.value?.setOnCompletionListener {
                            isPlaying.value = false
                            currentlyPlaying.value = null
                        }
                        PlayMusic(currentMediaPlayer.value)
                        currentlyPlaying.value = lastplayedMusic
                        println("currentPlayNew 11 == ${currentlyPlaying.value}")
                        isPlaying.value = true
                    }
                }
            )

        }

        var showDialog by remember { mutableStateOf(false) }
        var showBottomSheet by remember { mutableStateOf(false) }

        // Observe the first visible item index
        LaunchedEffect(lazyListState) {
            snapshotFlow { lazyListState.firstVisibleItemIndex }
                .collect { index ->
                    dropCount.value = index
                }
        }
        // Determine the background image source
        val filePath = ""
        val imageBitmap: ImageBitmap? = remember(filePath) {
            filePath?.let {
                val file = File(it)
                if (file.exists()) {
                    BitmapFactory.decodeFile(file.absolutePath)?.asImageBitmap()
                } else null
            }
        }
        var drawableRes by remember { mutableStateOf(R.drawable.diyanamalai_temple_back) } // Shared state for background color

        var malaiSeeds by remember { mutableStateOf(R.drawable.diyanamalai_rudraksham_seeds1) } // Shared state for background color

        var malaiThread by remember { mutableStateOf(R.drawable.diyanamalai_red_thread) } // Shared state for background color

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Load background image based on availability
            when {
                imageBitmap != null -> {
                    // Load from file
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = "Background Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                true -> {
                    // Load from drawable
                    Image(
                        painter = painterResource(id = drawableRes),
                        contentDescription = "Background Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                else -> {
                    // Optional: Placeholder or fallback background
                }
            }
            LazyColumn(
                state = lazyListState,
                userScrollEnabled = false,
                reverseLayout = true,
                modifier = Modifier
                    .fillMaxSize()
                    .then(swipeGestureModifier)
                    .padding(start = 10.dp)
                    .nestedScroll(nestedScrollConnection),
                verticalArrangement = Arrangement.Top, // Ensures no extra spacing between items
                contentPadding = PaddingValues(0.dp) // Removes any additional padding
            ) {
                items(2050) {
                    ImageItem(malaiSeeds, malaiThread, it)
                }
            }
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .align(Alignment.CenterEnd),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.End
            ) {

                Spacer(modifier = Modifier.size(10.dp))

                Box(
                    modifier = Modifier
                        .width(75.dp)
                        .height(IntrinsicSize.Min)
                        .padding(12.dp)
                        .background(
                            color = colorResource(R.color.diaynamaalai_dark_yellow),
                            shape = RoundedCornerShape(
                                topStart = 50.dp,
                                topEnd = 50.dp,
                                bottomEnd = 50.dp,
                                bottomStart = 50.dp
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 7.dp, vertical = 7.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.diyanamalai_refresh_icon),
                            contentDescription = "Refresh icon",
                            modifier = Modifier
                                .size(23.dp)
                                .padding(top = 5.dp)
                                .align(Alignment.CenterHorizontally)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(
                                        bounded = false,
                                        radius = 30.dp,
                                        color = Color.White
                                    ),
                                    onClick = {
                                        coroutineScope.launch {
                                            lazyListState.scrollToItem(0) // Reset scroll position to the top
                                            dropCount.value = 0         // Reset scroll count
                                        }
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.size(10.dp))

                        val dropBeatcount = if (dropCount.value >= 10) {
                            dropCount.value
                        } else {
                            "0" + dropCount.value
                        }
                        Text(
                            text = "" + dropBeatcount,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White,
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 10.dp, bottom = 10.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(
                                        bounded = false,
                                        radius = 30.dp,
                                        color = Color.White
                                    ),
                                    onClick = {
                                        showDialog = true
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Image(
                            painter = painterResource(R.drawable.diyanmalai_plus_icon),
                            contentDescription = "Plus icon",
                            modifier = Modifier
                                .size(23.dp)
                                .padding(bottom = 5.dp)
                                .align(Alignment.CenterHorizontally)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(
                                        bounded = false,
                                        radius = 30.dp,
                                        color = Color.White
                                    ),
                                    onClick = {
                                        showDialog = true
                                    }
                                )
                        )
                        if (showDialog) {
                            ShowAddCountDia(
                                showDialog = showDialog,
                                onDismiss = { showDialog = false }
                            )
                        }
                    }
                }
                Image(
                    painter = painterResource(R.drawable.diyanamalai_settings),
                    contentDescription = "Default icon",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = false,
                                radius = 30.dp,
                                color = Color.White
                            ),
                            onClick = {
                                showBottomSheet = true
                            }
                        ),
                )

                ShowBottomSheetDialog(
                    onDismiss = { showBottomSheet = false },
                    LocalLifecycleOwner.current,
                    onbackgroundSelect = { selectedBackground ->
                        drawableRes = selectedBackground // Update Box color
                    },
                    onmalaiSelected = { selectedMalaiSeeds ->
                        malaiSeeds = selectedMalaiSeeds
                    },
                    onMaalaiThreadSelected = { selectedMalaiThred ->
                        malaiThread = selectedMalaiThred
                    },
                    showBottom = showBottomSheet,
                )
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowCongratsDialog(onDismiss: () -> Unit, reachedGoal: Boolean, onPlay: (Int) -> Unit) {

        val bottomSheetState =
            rememberModalBottomSheetState(
                skipPartiallyExpanded = false,
                confirmValueChange = { newState ->
                    newState != SheetValue.Hidden // **** Prevents hiding of the sheet *****
                })

        // Trigger sound playback when the bottom sheet becomes visible
        LaunchedEffect(reachedGoal) {
            println("reachedGoadl=== $reachedGoal")
            if (reachedGoal) {
                StopMusic(currentMediaPlayer.value)
                currentMediaPlayer.value = MediaPlayer.create(
                    this@DiyanamaalaiMainActivity,
                    R.raw.diyanamaalai_dingbell_sound
                )
                currentMediaPlayer.value?.setOnCompletionListener {
                    isPlaying.value = false
                    currentlyPlaying.value = null
                }
                currentMediaPlayer.value?.start()
                currentlyPlaying.value =
                    preference.getInt(this@DiyanamaalaiMainActivity, "LastLoadedSound")
                isPlaying.value = false
            } else {
                onPlay(preference.getInt(this@DiyanamaalaiMainActivity, "LastLoadedSound"))
            }
        }

        if (reachedGoal) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = { onDismiss() },
                modifier = Modifier.fillMaxSize(),
                dragHandle = {}
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize() // Make the content fill the screen height
                        .padding(top = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top // Center content
                ) {
                    Image(
                        painter = painterResource(R.drawable.rounded_image), // Replace with your drawable GIF resource
                        contentDescription = "GIF from Drawable",
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "உங்கள்  தியானம் நிறைவுற்றது . மேலும், தொடர மீண்டும் தொடர பட்டன் - ஐ  க்ளிக்  செய்யவும் .",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = colorResource(R.color.diaynamaalai_black),
                            fontFamily = customFontFamily,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding(top = 10.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(
                                color = colorResource(R.color.diaynamaalai_dark_yellow),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .border(
                                width = 1.dp, color = Color.White, shape = RoundedCornerShape(
                                    bottomEnd = 5.dp,
                                    bottomStart = 5.dp,
                                    topStart = 5.dp,
                                    topEnd = 5.dp
                                )
                            )
                            .clickable {
                                onDismiss()
                                onPlay(
                                    preference.getInt(
                                        this@DiyanamaalaiMainActivity,
                                        "LastLoadedSound"
                                    )
                                )
                            }
                    ) {
                        Text(
                            "மீண்டும் தொடர",
                            style = TextStyle(
                                fontSize = 15.sp,
                                color = colorResource(R.color.diaynamaalai_white),
                                fontFamily = customFontFamily,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier.padding(
                                top = 10.dp,
                                bottom = 10.dp,
                                start = 10.dp,
                                end = 10.dp
                            ),
                        )
                    }
                }


            }

        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowBottomSheetDialog(
        onDismiss: () -> Unit,
        lifecycleOwner: LifecycleOwner,
        onbackgroundSelect: (Int) -> Unit,
        onmalaiSelected: (Int) -> Unit,
        onMaalaiThreadSelected: (Int) -> Unit,
        showBottom: Boolean
    ) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
        var isOn by remember { mutableStateOf(true) }
        var isClickCount by remember { mutableStateOf(0) }
        val soundsPlayList = remember {
            */
/*          mutableStateListOf(
                          "தீ" to R.raw.diyanamaalai_fire_sound,
                          "ஓம்" to R.raw.diayanamaalai_om_sound,
                          "மணி" to R.raw.diyanamaalai_bell_sound,
                          "அலை" to R.raw.diayanamaalai_om_sound,
                          "மழை" to R.raw.diyanamaalai_rain_sound,
                          "நதி" to R.raw.diyanamaalai_water_sound,
                          "காற்று" to R.raw.diayanamaalai_om_sound,
                          "பறவை" to R.raw.diyanamaalai_bird_sound
                      )*//*

            mutableStateListOf(
                "ஓம்" to R.raw.diayanamaalai_om_sound,
                "மணி" to R.raw.diyanamaalai_bell_sound,
                "மழை" to R.raw.diyanamaalai_rain_sound,
                "நதி" to R.raw.diyanamaalai_water_sound,
                "பறவை" to R.raw.diyanamaalai_bird_sound
            )

        }
        val chooseMalaiList = remember {
            mutableStateListOf(
                R.drawable.diyanamalai_rudhraksha_select to R.drawable.diyanamalai_rudraksham_seeds1,
                R.drawable.diyanamalai_karungali_select to R.drawable.diyanamalai_karungalai,
            )
        }

        val threadPlayList = remember {
            mutableStateListOf(
                R.drawable.diyanamalai_red_thread,
                R.drawable.diyanamalai_yellow_thread
            )
        }

        val backGroundList = remember {
            mutableStateListOf(
                R.drawable.diyanamalai_back_select_temple to R.drawable.diyanamalai_temple_back,
                R.drawable.diyanamalai_back_select_om to R.drawable.diyanamalai_om_back,
                R.drawable.diyanamaalai_yellow_background to R.drawable.diyanamaalai_yellow_background,
                R.drawable.diynamaalai_background_yellow1 to R.drawable.diynamaalai_background_yellow1,
                R.drawable.diyanamaalai_mediation_lord_shiva to R.drawable.diyanamaalai_mediation_lord_shiva,
                R.drawable.diyanamaalai_shiva_background1 to R.drawable.diyanamaalai_shiva_background1

            )
        }


        LaunchedEffect(Unit) {
            // Initialize and start the MediaPlayer when the screen is displayed
            preference.putInt(
                this@DiyanamaalaiMainActivity,
                "LastLoadedSound",
                soundsPlayList[0].second
            )
            preference.putBoolean(this@DiyanamaalaiMainActivity, "Toggle_State", true)
            currentMediaPlayer.value =
                MediaPlayer.create(this@DiyanamaalaiMainActivity, soundsPlayList[0].second).apply {
                    isLooping = true // Optional: Loop the music
                    start()
                }
            isPlaying.value = true
        }

        // Handle app lifecycle
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                println("event Of destr == $event")
                if (event == Lifecycle.Event.ON_PAUSE) {
                    PauseMusic(currentMediaPlayer.value)
                    currentMediaPlayer.value = null
                    isPlaying.value = false
                    isPaused.value = true
                }
                if (event == Lifecycle.Event.ON_STOP) {
                    StopMusic(currentMediaPlayer.value)
                    currentMediaPlayer.value = null
                    isPlaying.value = false
                }

                if (event == Lifecycle.Event.ON_RESUME) {
                    println("isPaused.value == ${isPaused.value}")
                    if (isPaused.value) {
                        val lastplaySound =
                            preference.getInt(this@DiyanamaalaiMainActivity, "LastLoadedSound")
                        println("lastPlay == $lastplaySound")
                        if (lastplaySound != 0) {
                            currentMediaPlayer.value = MediaPlayer.create(
                                this@DiyanamaalaiMainActivity,
                                lastplaySound
                            )
                            currentMediaPlayer.value?.setOnCompletionListener {
                                isPlaying.value = false
                                currentlyPlaying.value = null
                            }
                            PlayMusic(currentMediaPlayer.value)
                            currentlyPlaying.value = lastplaySound
                            isPlaying.value = true
                            isPaused.value = false
                        }
                    }
                }

            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
                StopMusic(currentMediaPlayer.value)
                currentMediaPlayer.value = null
            }
        }
        */
/*      LaunchedEffect(showBottom) {
                  if (showBottom){
                      isShowBottom.value = true
                  }else{
                      isShowBottom.value = false
                  }
              }*//*


        if (showBottom) {
            ModalBottomSheet(
                onDismissRequest = {
                    onDismiss()
                    // Trigger sound playback
                    println("selecte == $selectedSoundNew")

                },
                sheetState = sheetState,
                dragHandle = {},
                containerColor = colorResource(R.color.diaynamaalai_black),
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
                        text = "அமைப்பு",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White,
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Spacer(Modifier.height(10.dp))
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(
                            text = "மந்திர எண்ணிக்கை",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White,
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Start)
                        )
                        if (preference.getInt(this@DiyanamaalaiMainActivity, "Click_Count") == 0) {
                            preference.putInt(this@DiyanamaalaiMainActivity, "Click_Count", 11)
                        }
                        CountShowUi(isClickCount = isClickCount) { updatecount ->
                            preference.putInt(
                                this@DiyanamaalaiMainActivity,
                                "Click_Count",
                                updatecount
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(15.dp))

                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
                        ) {
                            Text(
                                text = "மந்திர இசை",
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Normal
                                ),
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(15.dp))

                            OnOffToggleSwitch(isOn = isOn) { newState ->
                                preference.putBoolean(
                                    this@DiyanamaalaiMainActivity,
                                    "Toggle_State",
                                    newState
                                )
                                isOn = newState
                                if (!newState) {
                                    preference.putInt(
                                        this@DiyanamaalaiMainActivity,
                                        "LastLoadedSound",
                                        0
                                    )
                                    StopMusic(currentMediaPlayer.value)
                                    currentMediaPlayer.value = null
                                } else {
                                    preference.putInt(
                                        this@DiyanamaalaiMainActivity,
                                        "LastLoadedSound",
                                        soundsPlayList[0].second
                                    )
                                    currentMediaPlayer.value = MediaPlayer.create(
                                        this@DiyanamaalaiMainActivity,
                                        soundsPlayList[0].second
                                    )
                                    currentMediaPlayer.value?.setOnCompletionListener {
                                        isPlaying.value = false
                                        currentlyPlaying.value = null
                                    }
                                    PlayMusic(currentMediaPlayer.value)
                                    currentlyPlaying.value = soundsPlayList[0].second
                                    isPlaying.value = true

                                }
                            }
                        }
                        //MusicPlayerWithRawFile(isOn)
                        if (preference.getBoolean(this@DiyanamaalaiMainActivity, "Toggle_State")) {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 72.dp),
                                contentPadding = PaddingValues(
                                    start = 2.dp,
                                    top = 10.dp,
                                    end = 2.dp
                                ), // Remove all padding
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(10.dp), // Space items evenly vertically
                                horizontalArrangement = Arrangement.SpaceBetween // Space items evenly horizontally
                            ) {
                                items(soundsPlayList.size) { pos ->
                                    println("currentPlayNew == ${currentlyPlaying.value}")
                                    isSelected.value =
                                        currentlyPlaying.value == soundsPlayList[pos].second

                                    LocalMusicPlayer(
                                        soundsPlayList,
                                        isSelected.value,
                                        pos,
                                        onPlay = { selectedMusic ->
                                            if (isSelected.value && isPlaying.value) {
                                                // Pause the currently playing sound
                                                println("SelectsBool 1 == ${isSelected.value}")
                                                println("SelectsBoolNew 1 == $isPlaying.value")
                                                StopMusic(currentMediaPlayer.value)
                                                currentMediaPlayer.value = MediaPlayer.create(
                                                    this@DiyanamaalaiMainActivity,
                                                    selectedMusic
                                                )
                                                currentMediaPlayer.value?.setOnCompletionListener {
                                                    isPlaying.value = false
                                                    currentlyPlaying.value = null
                                                }
                                                PlayMusic(currentMediaPlayer.value)
                                                currentlyPlaying.value = selectedMusic
                                                isPlaying.value = false
                                            } else {
                                                println("SelectsBool == ${isSelected.value}")
                                                println("SelectsBoolNew == ${isPlaying.value}")
                                                // Play the selected sound
                                                StopMusic(currentMediaPlayer.value)

                                                currentMediaPlayer.value = MediaPlayer.create(
                                                    this@DiyanamaalaiMainActivity,
                                                    selectedMusic
                                                )
                                                currentMediaPlayer.value?.setOnCompletionListener {
                                                    isPlaying.value = false
                                                    currentlyPlaying.value = null
                                                }
                                                PlayMusic(currentMediaPlayer.value)
                                                currentlyPlaying.value = selectedMusic
                                                isPlaying.value = true
                                            }
                                        },
                                        onStop = { playedMusic ->
                                            StopMusic(currentMediaPlayer.value)
                                            currentMediaPlayer.value = null
                                        })
                                }
                            }
                        }

                    }

                    Spacer(modifier = Modifier.size(20.dp))

                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(
                            text = "பின்னணி அமைப்பு",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White,
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Start)
                        )
                        BackgroundChooseUi(onbackgroundSelect, onDismiss, backGroundList)

                    }

                    Spacer(modifier = Modifier.size(20.dp))

                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(
                            text = "மணியைத் தேர்ந்தெடு",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White,
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Start)
                        )
                        MalaiChooseUi(onmalaiSelected, chooseMalaiList)
                    }

                    Spacer(modifier = Modifier.size(20.dp))

                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(
                            text = "நூலைத் தேர்ந்தெடு",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White,
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Start)
                        )
                        LoadThreadUi(threadPlayList, onMaalaiThreadSelected)
                    }
                }
            }
        }
    }

    fun PlayMusic(currentMediaPlayer: MediaPlayer?) {
        currentMediaPlayer?.start()
        currentMediaPlayer?.isLooping = true
    }

    fun StopMusic(currentMediaPlayer: MediaPlayer?) {
        currentMediaPlayer?.stop()
        currentMediaPlayer?.release()
        //  currentMediaPlayer = null

    }

    fun PauseMusic(currentMediaPlayer: MediaPlayer?) {
        currentMediaPlayer?.pause()
        //  currentMediaPlayer = null

    }

    private @Composable
    fun LocalMusicPlayer(
        soundsPlayList: SnapshotStateList<Pair<String, Int>>,
        isSelected: Boolean,
        position: Int,
        onPlay: (Int) -> Unit,
        onStop: (Int) -> Unit
    ) {

        val brownColor = Color(0xFFA87612) // Brown color
        val whiteColor = Color(0xFFFFFFFF) // White color



        Box(
            modifier = Modifier
                .padding(5.dp)
                .width(70.dp)
                .wrapContentHeight()
                .background(
                    color = (if (isSelected) whiteColor else Color.Transparent),
                    shape = RoundedCornerShape(5.dp)
                )
                .border(
                    width = 1.dp, color = Color.White, shape = RoundedCornerShape(
                        bottomEnd = 5.dp,
                        bottomStart = 5.dp,
                        topStart = 5.dp,
                        topEnd = 5.dp
                    )
                )
                .clickable {
                    println("select post sec === ${soundsPlayList[position].second}")
                    println("select post first === ${soundsPlayList[position].first}")

                    preference.putInt(this@DiyanamaalaiMainActivity, "clickPosColor", position)

                    preference.putInt(
                        this@DiyanamaalaiMainActivity,
                        "LastLoadedSound",
                        soundsPlayList[position].second
                    )
                    // onStop(soundsPlayList[position].second)

                    onPlay(soundsPlayList[position].second)


                }
        ) {
            Text(
                text = soundsPlayList[position].first,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = if (isSelected) brownColor else whiteColor,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
                    .padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 5.dp)
            )
        }
    }

    @Composable
    fun LoadThreadUi(
        threadPlayList: SnapshotStateList<Int>,
        onMaalaiThreadSelected: (Int) -> Unit
    ) {
        val scrollState = rememberScrollState()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(
                    top = 20.dp, bottom = 5.dp, start = 2.dp, end = 10.dp
                )
        ) {
            // Add items inside the Row
            for (index in threadPlayList.indices) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .clickable {
                            maalaiThreadSeletedPos.value = index
                            onMaalaiThreadSelected(threadPlayList[index])
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (maalaiThreadSeletedPos.value == index) {
                        println("selected == ${maalaiSelectedPosition.value}")
                        ThreadImageItemSelected(threadPlayList[index])
                    } else {
                        ThreadImageItem(threadPlayList[index])
                    }

                }
            }
        }
    }

    private @Composable
    fun ThreadImageItemSelected(imageOfThread: Int) {
        Image(
            painter = painterResource(id = imageOfThread),
            contentDescription = "Rudraksh",
            modifier = Modifier
                .width(100.dp)
                .height(IntrinsicSize.Max)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.diaynamaalai_black),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(start = 5.dp, top = 2.dp, bottom = 2.dp, end = 5.dp),
        )
    }


    private @Composable
    fun ThreadImageItem(imageOfThread: Int) {

        Image(
            painter = painterResource(id = imageOfThread),
            contentDescription = "Rudraksh",
            modifier = Modifier
                .width(85.dp)
                .height(IntrinsicSize.Max)
                .padding(start = 5.dp, top = 2.dp, bottom = 2.dp, end = 5.dp),
        )
    }

    private @Composable
    fun CountShowUi(isClickCount: Int, onClickCount: (Int) -> Unit) {
        println("clickCount == ${preference.getInt(this@DiyanamaalaiMainActivity, "Click_Count")}")
        val colorCode = when {
            preference.getInt(this@DiyanamaalaiMainActivity, "Click_Count") == 11 -> {
                Color.White
            }

            else -> {
                Color.Transparent
            }
        }

        val colorCode1 = when {
            preference.getInt(this@DiyanamaalaiMainActivity, "Click_Count") == 21 -> {
                Color.White
            }

            else -> {
                Color.Transparent
            }
        }

        val colorCode2 = when {
            preference.getInt(this@DiyanamaalaiMainActivity, "Click_Count") == 54 -> {
                Color.White
            }

            else -> {
                Color.Transparent
            }
        }

        val colorCode3 = when {
            preference.getInt(
                this@DiyanamaalaiMainActivity,
                "Click_Count"
            ) != 11 && preference.getInt(
                this@DiyanamaalaiMainActivity,
                "Click_Count"
            ) != 21 && preference.getInt(this@DiyanamaalaiMainActivity, "Click_Count") != 54 -> {
                Color.White
            }

            else -> {
                Color.Transparent
            }
        }

        var backgroundColor by remember { mutableStateOf(colorCode) }
        var backgroundColor1 by remember { mutableStateOf(colorCode1) }
        var backgroundColor2 by remember { mutableStateOf(colorCode2) }
        var backgroundColor3 by remember { mutableStateOf(colorCode3) }

        var showDialog by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(
                        width = 1.dp, color = Color.White, shape = RoundedCornerShape(
                            bottomEnd = 5.dp,
                            bottomStart = 5.dp,
                            topStart = 5.dp,
                            topEnd = 5.dp
                        )
                    )
                    .background(backgroundColor, shape = RoundedCornerShape(5.dp))
                    .clickable {
                        onClickCount(11)
                        backgroundColor1 = Color.Transparent
                        backgroundColor2 = Color.Transparent
                        backgroundColor3 = Color.Transparent
                        backgroundColor = if (preference.getInt(
                                this@DiyanamaalaiMainActivity,
                                "Click_Count"
                            ) == 11
                        ) Color.White else Color.Transparent
                    }

            ) {
                Text(
                    text = "11",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = (if (backgroundColor == Color.Transparent) Color.White else colorResource(
                            R.color.diaynamaalai_dark_yellow
                        )),
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 14.dp, top = 3.dp, end = 14.dp, bottom = 3.dp)
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 20.dp)
                    .border(
                        width = 1.dp, color = Color.White, shape = RoundedCornerShape(
                            bottomEnd = 5.dp,
                            bottomStart = 5.dp,
                            topStart = 5.dp,
                            topEnd = 5.dp
                        )
                    )
                    .background(backgroundColor1, shape = RoundedCornerShape(5.dp))
                    .clickable {
                        onClickCount(21)
                        backgroundColor = Color.Transparent
                        backgroundColor2 = Color.Transparent
                        backgroundColor3 = Color.Transparent
                        backgroundColor1 = if (preference.getInt(
                                this@DiyanamaalaiMainActivity,
                                "Click_Count"
                            ) == 21
                        ) Color.White else Color.Transparent
                    }
            ) {
                Text(
                    text = "21",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = (if (backgroundColor1 == Color.Transparent) Color.White else colorResource(
                            R.color.diaynamaalai_dark_yellow
                        )),
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 14.dp, top = 3.dp, end = 14.dp, bottom = 3.dp)
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 20.dp)
                    .border(
                        width = 1.dp, color = Color.White, shape = RoundedCornerShape(
                            bottomEnd = 5.dp,
                            bottomStart = 5.dp,
                            topStart = 5.dp,
                            topEnd = 5.dp
                        )
                    )
                    .background(backgroundColor2, shape = RoundedCornerShape(5.dp))
                    .clickable {
                        onClickCount(54)
                        backgroundColor = Color.Transparent
                        backgroundColor1 = Color.Transparent
                        backgroundColor3 = Color.Transparent
                        backgroundColor2 = if (preference.getInt(
                                this@DiyanamaalaiMainActivity,
                                "Click_Count"
                            ) == 54
                        ) Color.White else Color.Transparent
                    }

            ) {
                Text(
                    text = "54",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = (if (backgroundColor2 == Color.Transparent) Color.White else colorResource(
                            R.color.diaynamaalai_dark_yellow
                        )),
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 14.dp, top = 3.dp, end = 14.dp, bottom = 3.dp)
                )
            }

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 20.dp)
                    .border(
                        width = 1.dp, color = Color.White, shape = RoundedCornerShape(
                            bottomEnd = 5.dp,
                            bottomStart = 5.dp,
                            topStart = 5.dp,
                            topEnd = 5.dp
                        )
                    )
                    .background(backgroundColor3, shape = RoundedCornerShape(5.dp))
                    .clickable {
                        backgroundColor = Color.Transparent
                        backgroundColor2 = Color.Transparent
                        backgroundColor1 = Color.Transparent


                        if (preference.getInt(
                                this@DiyanamaalaiMainActivity,
                                "Click_Count"
                            ) != 11 && preference.getInt(
                                this@DiyanamaalaiMainActivity,
                                "Click_Count"
                            ) != 21 && preference.getInt(
                                this@DiyanamaalaiMainActivity,
                                "Click_Count"
                            ) != 54
                        )
                            backgroundColor3 = Color.White
                        else {
                            backgroundColor3 =
                                if (backgroundColor3 == Color.Transparent) Color.White else Color.Transparent // Reset to transparent
                        }

                        showDialog = true
                    }

            ) {
                Text(
                    text = "Custom",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = (if (backgroundColor3 == Color.Transparent) Color.White else colorResource(
                            R.color.diaynamaalai_dark_yellow
                        )),
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 14.dp, top = 3.dp, end = 14.dp, bottom = 3.dp)
                )
            }
            if (showDialog) {
                ShowAddCountDia(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false }
                )
            }
        }
    }

    private @Composable
    fun MalaiChooseUi(
        onmalaiSelected: (Int) -> Unit,
        chooseMalaiList: SnapshotStateList<Pair<Int, Int>>
    ) {
        val scrollState = rememberScrollState()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(top = 20.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        ) {
            // Add items inside the Row
            for (index in chooseMalaiList.indices) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp, end = 15.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 5.dp,
                                bottomStart = 5.dp,
                                topStart = 5.dp,
                                topEnd = 5.dp
                            )
                        )
                        .clickable {
                            maalaiSelectedPosition.value = index
                            onmalaiSelected(chooseMalaiList[index].second)
                        }
                ) {
                    if (maalaiSelectedPosition.value == index) {
                        println("selected == ${maalaiSelectedPosition.value}")

                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(
                                    RoundedCornerShape(
                                        bottomEnd = 10.dp,
                                        bottomStart = 10.dp,
                                        topStart = 10.dp,
                                        topEnd = 10.dp
                                    )
                                )
                        ) {
                            // Background Image
                            Image(
                                painter = painterResource(id = chooseMalaiList[index].first), // Replace with your image resource
                                contentDescription = "Temple Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(90.dp)
                                    .height(90.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )

                            Image(
                                painter = painterResource(id = R.drawable.diyanamalai_select_icon_small),
                                contentDescription = "Selected",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 5.dp, end = 5.dp)
                                    .size(24.dp) // Adjust the size of the checkmark
                            )
                        }
                    } else {
                        Image(
                            painter = painterResource(id = chooseMalaiList[index].first),
                            contentDescription = "OmBack",
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp),
                        )
                    }
                }

            }
        }

    }

    @Composable
    fun BackgroundChooseUi(
        onbackgroundSelect: (Int) -> Unit,
        onDismiss: () -> Unit,
        backGroundList: SnapshotStateList<Pair<Int, Int>>
    ) {

        val scrollState = rememberScrollState()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(top = 20.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        ) {
            // Add items inside the Row
            for (index in backGroundList.indices) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 7.dp, end = 7.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 5.dp,
                                bottomStart = 5.dp,
                                topStart = 5.dp,
                                topEnd = 5.dp
                            )
                        )
                        .clickable {
                            backGroundselectedPosition.value = index
                            onbackgroundSelect(backGroundList[index].second)
                        }
                ) {
                    if (backGroundselectedPosition.value == index) {
                        println("selected == ${backGroundselectedPosition.value}")
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(
                                    RoundedCornerShape(
                                        bottomEnd = 10.dp,
                                        bottomStart = 10.dp,
                                        topStart = 10.dp,
                                        topEnd = 10.dp
                                    )
                                )
                        ) {
                            // Background Image
                            Image(
                                painter = painterResource(id = backGroundList[index].first), // Replace with your image resource
                                contentDescription = "Temple Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(110.dp)
                                    .height(130.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )

                            Image(
                                painter = painterResource(id = R.drawable.diyanamalai_select_icon_small),
                                contentDescription = "Selected",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 5.dp, end = 5.dp)
                                    .size(24.dp) // Adjust the size of the checkmark
                            )
                        }
                    } else {
                        Image(
                            painter = painterResource(id = backGroundList[index].second),
                            contentDescription = "OmBack",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(100.dp)
                                .height(120.dp)
                                .padding(start = 10.dp)
                                .clip(shape = RoundedCornerShape(10.dp)),
                        )
                    }
                }
            }
        }
    }

    private @Composable
    fun OnOffToggleSwitch(isOn: Boolean, onToggle: (Boolean) -> Unit) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(50)
                )
                */
/*  .clickable { onToggle(!isOn) }*//*

                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null // Disables the ripple effect
                ) {
                    onToggle(!isOn)
                }
                .width(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp))
                        .background(
                            if (preference.getBoolean(
                                    this@DiyanamaalaiMainActivity,
                                    "Toggle_State"
                                )
                            ) Color.White else Color.Transparent
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ON",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = if (preference.getBoolean(
                                    this@DiyanamaalaiMainActivity,
                                    "Toggle_State"
                                )
                            ) colorResource(R.color.diaynamaalai_dark_yellow) else Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
                        .background(
                            if (!preference.getBoolean(
                                    this@DiyanamaalaiMainActivity,
                                    "Toggle_State"
                                )
                            ) Color.White else Color.Transparent
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "OFF",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = if (!preference.getBoolean(
                                    this@DiyanamaalaiMainActivity,
                                    "Toggle_State"
                                )
                            ) colorResource(R.color.diaynamaalai_dark_yellow) else Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowAddCountDia(showDialog: Boolean, onDismiss: () -> Unit) {

        val maxLength = 4


        countField.value = "" + preference.getInt(this@DiyanamaalaiMainActivity, "Click_Count")
        if (showDialog) {
            Dialog(
                onDismissRequest = onDismiss,
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false,
                    usePlatformDefaultWidth = true,
                    decorFitsSystemWindows = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent) // Semi-transparent background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(
                                    top = 10.dp,
                                    bottom = 10.dp,
                                    start = 5.dp,
                                    end = 5.dp
                                )
                                .align(Alignment.Center)
                        ) {
                            Text(
                                text = "மந்திர எண்ணிக்கை",
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Color.Black,
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .wrapContentSize()
                            )
                            Spacer(modifier = Modifier.padding(top = 15.dp))
                            Row {

                                Icon(
                                    painter = painterResource(R.drawable.diynamalai_baseline_remove_24),
                                    contentDescription = "subtract_icon",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.CenterVertically)
                                        .clickable {
                                            val currentCount = countField.value.toIntOrNull() ?: 0
                                            if (currentCount > 0) countField.value =
                                                (currentCount - 1).toString() // Decrement count
                                        }
                                )
                                Spacer(modifier = Modifier.padding(end = 10.dp))
                                TextField(
                                    value = countField.value,
                                    onValueChange = { input ->
                                        if (input.length <= maxLength) {
                                            countField.value = input
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number, // Input type for Decimal
                                    ),
                                    textStyle = TextStyle(
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        fontSize = 15.sp
                                    ),
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(44.dp) // Minimum height for the TextField
                                        .padding(0.dp) // Remove additional padding to minimize space
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                        .background(Color.Transparent),
                                    colors = TextFieldDefaults.textFieldColors(
                                        // Set the underline color to transparent
                                        focusedIndicatorColor = Color.Transparent, // Border color when focused
                                        unfocusedIndicatorColor = Color.Transparent, // Border color when not focused
                                        disabledIndicatorColor = Color.Transparent,
                                        containerColor = Color.Transparent
                                    ),
                                    singleLine = true
                                )

                                Spacer(modifier = Modifier.padding(start = 10.dp))
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "add_icon",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.CenterVertically)
                                        .clickable {
                                            val currentCount = countField.value.toIntOrNull() ?: 0
                                            if (currentCount < 9999) {
                                                countField.value = (currentCount + 1).toString()
                                            }

                                        }
                                )

                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            println(
                                "clickCount == ${
                                    preference.getInt(
                                        this@DiyanamaalaiMainActivity,
                                        "Click_Count"
                                    )
                                }"
                            )

                            val colorCode = when {
                                preference.getInt(
                                    this@DiyanamaalaiMainActivity,
                                    "Click_Count"
                                ) == 11 -> {
                                    colorResource(R.color.diaynamaalai_dark_yellow)
                                }

                                else -> {
                                    Color.Transparent
                                }
                            }
                            val colorCode1 = when {
                                preference.getInt(
                                    this@DiyanamaalaiMainActivity,
                                    "Click_Count"
                                ) == 21 -> {
                                    colorResource(R.color.diaynamaalai_dark_yellow)
                                }

                                else -> {
                                    Color.Transparent
                                }
                            }
                            val colorCode2 = when {
                                preference.getInt(
                                    this@DiyanamaalaiMainActivity,
                                    "Click_Count"
                                ) == 54 -> {
                                    colorResource(R.color.diaynamaalai_dark_yellow)
                                }

                                else -> {
                                    Color.Transparent
                                }
                            }

                            var backgroundColor by remember { mutableStateOf(colorCode) }
                            var backgroundColor1 by remember { mutableStateOf(colorCode1) }
                            var backgroundColor2 by remember { mutableStateOf(colorCode2) }

                            //Box design for select count
                            Row(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 15.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                            ) {

                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(
                                                bottomEnd = 5.dp,
                                                bottomStart = 5.dp,
                                                topStart = 5.dp,
                                                topEnd = 5.dp
                                            )
                                        )
                                        .background(
                                            backgroundColor,
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                        .clickable {
                                            backgroundColor1 = Color.Transparent
                                            backgroundColor2 = Color.Transparent
                                            if (preference.getInt(
                                                    this@DiyanamaalaiMainActivity,
                                                    "Click_Count"
                                                ) == 11
                                            ) {
                                                backgroundColor = colorCode
                                            } else {
                                                backgroundColor =
                                                    if (backgroundColor == Color.Transparent) Color(
                                                        0xFFA87612
                                                    ) else Color.Transparent // Reset to transparent
                                            }

                                            countField.value = "" + 11
                                        }

                                ) {
                                    Text(
                                        text = "11",
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = (if (backgroundColor == colorResource(R.color.diaynamaalai_dark_yellow)) Color.White else colorResource(
                                                R.color.diaynamaalai_dark_yellow
                                            )),
                                            fontFamily = customFontFamily,
                                            fontWeight = FontWeight.Normal
                                        ),
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(
                                                start = 20.dp,
                                                top = 5.dp,
                                                end = 20.dp,
                                                bottom = 5.dp
                                            )
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 20.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(
                                                bottomEnd = 5.dp,
                                                bottomStart = 5.dp,
                                                topStart = 5.dp,
                                                topEnd = 5.dp
                                            )
                                        )
                                        .background(
                                            backgroundColor1,
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                        .clickable {
                                            backgroundColor = Color.Transparent
                                            backgroundColor2 = Color.Transparent
                                            if (preference.getInt(
                                                    this@DiyanamaalaiMainActivity,
                                                    "Click_Count"
                                                ) == 21
                                            ) {
                                                backgroundColor1 = colorCode1
                                            } else {
                                                backgroundColor1 =
                                                    if (backgroundColor1 == Color.Transparent) Color(
                                                        0xFFA87612
                                                    ) else Color.Transparent // Reset to transparent

                                            }
                                            countField.value = "" + 21
                                        }

                                ) {
                                    Text(
                                        text = "21",
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = (if (backgroundColor1 == colorResource(R.color.diaynamaalai_dark_yellow)) Color.White else colorResource(
                                                R.color.diaynamaalai_dark_yellow
                                            )),
                                            fontFamily = customFontFamily,
                                            fontWeight = FontWeight.Normal
                                        ),
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(
                                                start = 20.dp,
                                                top = 5.dp,
                                                end = 20.dp,
                                                bottom = 5.dp
                                            )
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 20.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(
                                                bottomEnd = 5.dp,
                                                bottomStart = 5.dp,
                                                topStart = 5.dp,
                                                topEnd = 5.dp
                                            )
                                        )
                                        .background(
                                            backgroundColor2,
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                        .clickable {
                                            backgroundColor = Color.Transparent
                                            backgroundColor1 = Color.Transparent
                                            if (preference.getInt(
                                                    this@DiyanamaalaiMainActivity,
                                                    "Click_Count"
                                                ) == 54
                                            ) {
                                                backgroundColor2 = colorCode2
                                            } else {
                                                backgroundColor2 =
                                                    if (backgroundColor2 == Color.Transparent) Color(
                                                        0xFFA87612
                                                    ) else Color.Transparent // Reset to transparent
                                            }
                                            countField.value = "" + 54
                                        }

                                ) {
                                    Text(
                                        text = "54",
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = (if (backgroundColor2 == colorResource(R.color.diaynamaalai_dark_yellow)) Color.White else colorResource(
                                                R.color.diaynamaalai_dark_yellow
                                            )),
                                            fontFamily = customFontFamily,
                                            fontWeight = FontWeight.Normal
                                        ),
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(
                                                start = 20.dp,
                                                top = 5.dp,
                                                end = 20.dp,
                                                bottom = 5.dp
                                            )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(15.dp))
                            Row(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .wrapContentHeight()
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(15.dp)
                                        )
                                        .background(
                                            colorResource(R.color.diaynamaalai_white),
                                            shape = RoundedCornerShape(15.dp)
                                        )
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null // Disables the ripple effect
                                        ) {
                                            onDismiss()
                                        }
                                ) {
                                    Text(
                                        text = "Close",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            color = colorResource(R.color.diaynamaalai_dark_yellow),
                                            fontWeight = FontWeight.Bold
                                        ),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(
                                                end = 20.dp,
                                                start = 20.dp,
                                                top = 10.dp,
                                                bottom = 10.dp
                                            )
                                            .align(Alignment.Center)
                                    )
                                }
                                Spacer(modifier = Modifier.size(10.dp))
                                Box(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .wrapContentHeight()
                                        .border(
                                            width = 1.dp,
                                            color = Color.White,
                                            shape = RoundedCornerShape(15.dp)
                                        )
                                        .background(
                                            colorResource(R.color.diaynamaalai_dark_yellow),
                                            shape = RoundedCornerShape(15.dp)
                                        )
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null // Disables the ripple effect
                                        ) {
                                            preference.putInt(
                                                this@DiyanamaalaiMainActivity,
                                                "Click_Count",
                                                countField.value.toInt()
                                            )
                                            onDismiss()
                                        }
                                ) {
                                    Text(
                                        text = "Submit",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(
                                                end = 20.dp,
                                                start = 20.dp,
                                                top = 10.dp,
                                                bottom = 10.dp
                                            )
                                            .align(Alignment.Center)
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun ImageItem(imageId: Int, malaiThread: Int, index: Int) {
        val indexnew = index.toString()
        val counterFieldNew = countField.value.toInt()
        println("indexNew =- $indexnew")
        println("indexNew 1 =- ${countField.value}")
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Rudraksh",
                modifier = Modifier
                    .width(180.dp) // Let the image take full width
                    .height(150.dp)
                    .padding(0.dp),
            )

            if (index == counterFieldNew - 1) {
                Image(
                    painter = painterResource(id = malaiThread),
                    contentDescription = "Maalai",
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center) // Align it to the center of the Box
                        .offset(y = (-75).dp) // Adjust overlap using offset
                )

            }

        }

    }
}*/
