package nithra.tamil.rasipalan.poojacarner

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlinx.coroutines.launch

class poojaClass : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoojaScreen()
        }
    }
}
@Composable
fun PoojaScreen() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            FullScreenImageWithCustomLayout()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FullScreenImageWithCustomLayout() {
    val context = LocalContext.current
    var showgodBottomSheet by remember { mutableStateOf(false) }
    var SlideUpToDownAnimation by remember { mutableStateOf(false) }
    var backgroundImage by remember { mutableStateOf(R.drawable.poojacarner_background6) }
    var selectedOverlayImage by remember { mutableStateOf<Int?>(R.drawable.vinayagar_pooja) }
    var isVilakkuOn by remember { mutableStateOf(false) }
    var isCoconutReplaced by remember { mutableStateOf(false) }
    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }
    var selectedImages: MutableList<Int> = remember { mutableStateListOf(0) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var isSoundOn by remember { mutableStateOf(true) }
    val selectedSound = R.raw.om_sound // Default sound
    var selectedFlowerRes by remember { mutableStateOf(R.drawable.poojacarnerflower1) } // Default flower

    // Different images for each deity
    val deityImages = mapOf(
        R.drawable.vinayagar_pooja to listOf(
            R.drawable.vinayagar_pooja, R.drawable.vinayagar_pooja, R.drawable.vinayagar_pooja, R.drawable.vinayagar_pooja,
            R.drawable.vinayagar_pooja, R.drawable.vinayagar_pooja, R.drawable.vinayagar_pooja, R.drawable.vinayagar_pooja,
            R.drawable.vinayagar_pooja, R.drawable.vinayagar_pooja
        ),
        R.drawable.iyappan to listOf(
            R.drawable.iyappan, R.drawable.iyappan, R.drawable.iyappan, R.drawable.iyappan,
            R.drawable.iyappan, R.drawable.iyappan, R.drawable.iyappan, R.drawable.iyappan,
            R.drawable.iyappan, R.drawable.iyappan
        ),
        R.drawable.murugan_pooja to listOf(
            R.drawable.murugan_pooja, R.drawable.murugan_pooja, R.drawable.murugan_pooja, R.drawable.murugan_pooja,
            R.drawable.murugan_pooja, R.drawable.murugan_pooja, R.drawable.murugan_pooja, R.drawable.murugan_pooja,
            R.drawable.murugan_pooja, R.drawable.murugan_pooja
        ),
        R.drawable.pooja_carner_lakshimi to listOf(
            R.drawable.pooja_carner_lakshimi, R.drawable.pooja_carner_lakshimi, R.drawable.pooja_carner_lakshimi, R.drawable.pooja_carner_lakshimi,
            R.drawable.pooja_carner_lakshimi, R.drawable.pooja_carner_lakshimi, R.drawable.pooja_carner_lakshimi, R.drawable.pooja_carner_lakshimi,
            R.drawable.pooja_carner_lakshimi, R.drawable.pooja_carner_lakshimi
        ),
        R.drawable.saraswathi to listOf(
            R.drawable.saraswathi, R.drawable.saraswathi, R.drawable.saraswathi, R.drawable.saraswathi,
            R.drawable.saraswathi, R.drawable.saraswathi, R.drawable.saraswathi, R.drawable.saraswathi,
            R.drawable.saraswathi, R.drawable.saraswathi
        ),
        R.drawable.perumaljpg to listOf(
            R.drawable.perumaljpg, R.drawable.perumaljpg, R.drawable.perumaljpg, R.drawable.perumaljpg,
            R.drawable.perumaljpg, R.drawable.perumaljpg, R.drawable.perumaljpg, R.drawable.perumaljpg,
            R.drawable.perumaljpg, R.drawable.perumaljpg
        ),
        R.drawable.sivan_new to listOf(
            R.drawable.sivan_new, R.drawable.sivan_new, R.drawable.sivan_new, R.drawable.sivan_new,
            R.drawable.sivan_new, R.drawable.sivan_new, R.drawable.sivan_new, R.drawable.sivan_new,
            R.drawable.sivan_new, R.drawable.sivan_new
        ),
        R.drawable.amman1 to listOf(
            R.drawable.amman1, R.drawable.amman1, R.drawable.amman1, R.drawable.amman1,
            R.drawable.amman1, R.drawable.amman1, R.drawable.amman1, R.drawable.amman1,
            R.drawable.amman1, R.drawable.amman1
        )
    )


    // Automatically start "Om Sound" when the page is entered
    LaunchedEffect(Unit) {
        if (isSoundOn) {
            mediaPlayer.value = MediaPlayer.create(context, selectedSound).apply {
                isLooping = true // Loop the sound
                start()
            }
        }
    }

    // Clean up the MediaPlayer when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.value?.stop()
            mediaPlayer.value?.release()
            mediaPlayer.value = null
        }
    }

    // Get the list of images for the selected deity, or an empty list if none is selected
    println("selectedOverlayImage == $selectedOverlayImage")
    println("selectedOverlayImage 11 == ${selectedImages.size}")
    selectedImages = (selectedOverlayImage?.let { deityImages[it] } ?: listOf()).toMutableList()
    println("selectedOverlayImage new 11 == ${selectedImages.size}")
    val pagerState = rememberPagerState(pageCount = { selectedImages.size }, initialPage = 0)
    var coroutine = rememberCoroutineScope()
    coroutine.launch {
        pagerState.scrollToPage(0)
    }


    val animatedStates = remember {
        mutableStateMapOf<Int, MutableState<Float>>(
            R.drawable.big_bell to mutableStateOf(90f),
            R.drawable.big_aarthi to mutableStateOf(90f),
            R.drawable.big_udhupakthi to mutableStateOf(90f),
        )
    }
    val rotationCounts = remember {
        mutableStateMapOf<Int, MutableState<Int>>(
            R.drawable.big_bell to mutableStateOf(
                3
            ),
            R.drawable.big_aarthi to mutableStateOf(0),
            R.drawable.big_udhupakthi to mutableStateOf(0),
        )
    }
    val maxRotations = 3 // Maximum rotations for each image


    animatedStates.forEach { (imageRes, animatedAngleState) ->
        val rotationCount = rotationCounts[imageRes] ?: mutableStateOf(0)
        println("rotationcount====${rotationCount.value}")
        LaunchedEffect(rotationCount.value) {
            if (rotationCount.value != 0) {
                if (rotationCount.value < maxRotations) {
                    println("imageclick===$imageRes")
                    if (imageRes == R.drawable.big_bell && rotationCount.value == 1) { // Start sound only once per rotation
                        mediaPlayer.value = MediaPlayer.create(context, R.raw.bell).apply {
                            isLooping = true // Loop the sound while rotating
                            start()
                        }
                    }

                    val duration = 5500L // Total duration for 3 rounds
                    val totalAngle = 550f // Total angle for 3 rounds
                    val increment = totalAngle / (duration / 16L) // Increment for each 16 ms frame
                    val startAngle = animatedAngleState.value // Start from the current angle
                    val startTime = System.currentTimeMillis()

                    while (System.currentTimeMillis() - startTime < duration) {
                        delay(16L) // Smooth animation at ~60 FPS
                        animatedAngleState.value = startAngle + ((System.currentTimeMillis() - startTime) / duration.toFloat()) * totalAngle
                    }

                    rotationCount.value++

                    if (rotationCount.value >= maxRotations) {
                        // Stop sound after 3 rounds
                        animatedAngleState.value = 90f
                        if (imageRes == R.drawable.big_bell) {
                            mediaPlayer.value?.stop()
                            mediaPlayer.value?.release()
                            mediaPlayer.value = null
                        }
                    }
                }
            }
        }

// Clean up media player on dispose
        DisposableEffect(Unit) {
            onDispose {
                mediaPlayer.value?.stop()
                mediaPlayer.value?.release()
                mediaPlayer.value = null
            }
        }
    }

    val density = LocalDensity.current


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = backgroundImage),
                contentDescription = "Default Background Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }

        // ViewPager for center images
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) { page ->
            Image(
                painter = painterResource(id = selectedImages[page]),
                contentDescription = "Paged Image $page",
                modifier = Modifier
                    .width(500.dp)
                    .height(400.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        // Circular animated images
        animatedStates.forEach { (imageRes, animatedAngleState) ->

            println("rrrrcccc===${rotationCounts[imageRes]?.value}")
            println("resorssss${imageRes}")
            println("aniiii===${animatedAngleState.value}")

            if ((rotationCounts[imageRes]?.value ?: 0) < maxRotations) {
                val radiusDp = 150.dp
                val radiusPx = with(density) { radiusDp.toPx() }
                val angleInRadians = Math.toRadians(animatedAngleState.value.toDouble())
                val offsetX = radiusPx * cos(angleInRadians).toFloat()
                val offsetY = radiusPx * sin(angleInRadians).toFloat()

                println("radiusss====$angleInRadians")

                if (animatedAngleState.value != 90f){
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Circular Moving Image",
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center)
                            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                    )
                }

            }
        }

        // First and second row layouts
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // First Row: Images that trigger circular animation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                val firstRowImages = listOf(
                    R.drawable.big_aarthi,
                    R.drawable.big_bell,
                    if (isVilakkuOn) R.drawable.vilakku_on else R.drawable.vilakku_off,
                    R.drawable.big_udhupakthi,
                    if (isCoconutReplaced) R.drawable.pooja_carner_coconut_open else R.drawable.coconut, // Toggle coconut/flower
                )
                firstRowImages.forEach { imageRes ->
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "First Row Image",
                        modifier = Modifier
                            .size(64.dp)
                            .clickable {
                                when (imageRes) {
                                    R.drawable.vilakku_off -> {
                                        isVilakkuOn = true // Turn on the Vilakku
                                    }

                                    R.drawable.vilakku_on -> {
                                        isVilakkuOn = false // Turn off the Vilakku
                                    }

                                    R.drawable.coconut -> {
                                        isCoconutReplaced = true // Replace coconut with flower
                                    }

                                    else -> {

                                        println("rotationcounddd===${rotationCounts[imageRes]}")



                                        if ((rotationCounts[imageRes]?.value
                                                ?: 0) >= maxRotations
                                        ) {
                                            // Reset rotation count if finished
                                            rotationCounts[imageRes]?.value = 1
                                        } else if (rotationCounts[imageRes]?.value == 0) {
                                            // Start animation for the first time
                                            rotationCounts[imageRes]?.value = 1
                                        }
                                    }
                                }
                            }
                    )
                }
            }

            // Second Row: Images with separate click actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val secondRowImages = listOf(
                    R.drawable.sivan_new,
                    R.drawable.setting,
                    R.drawable.poojacarnerflower1,
                )

                secondRowImages.forEach { imageRes ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape) // Clip the shape to a circle
                            .border(2.dp, Color.Black, CircleShape) // Add a circular border
                            .clickable {
                                when (imageRes) {
                                    R.drawable.sivan_new -> {
                                        Log.d("ImageClick", "God image clicked")
                                        showgodBottomSheet = true
                                        // Toggle bottom sheet logic
                                    }

                                    R.drawable.poojacarnerflower1 -> {
                                        SlideUpToDownAnimation = true
                                    }

                                    R.drawable.setting -> {
                                        showBottomSheet = true
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Second Row Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape) // Ensure the image is circular
                        )
                    }
                }
            }

        }

        // Flower animation
        println("selectedFlowerRes == $selectedFlowerRes")

        if (SlideUpToDownAnimation) {
            SlideUpToDownImageAnimation(
                selectedFlower = selectedFlowerRes, // Pass the selected flower
                onAnimationEnd = {
                    SlideUpToDownAnimation = false // Reset state after animation ends
                }
            )
        }


        // God Selection Bottom Sheet
        if (showgodBottomSheet) {
            ImageRowWithModalBottomSheet(
                onDismiss = { showgodBottomSheet = false },
                onImageSelected = { selectedImage ->
                    selectedOverlayImage = selectedImage
                    showgodBottomSheet = false
                }
            )
        }


        //setting bottomsheet
        // Bottom Sheet
        if (showBottomSheet) {
            SettingBottomSheet(
                onDismiss = { showBottomSheet = false },
                isSoundOn = isSoundOn,
                onToggleSound = { isSoundOn = it },
                onSoundSelected = { },
                onBackgroundChange = { newBackground -> backgroundImage = newBackground },
                onImageSelected = { selectedImage ->
                    selectedOverlayImage = selectedImage
                    showgodBottomSheet = false
                },
                onFlowerSelected = { flowerRes ->
                    selectedFlowerRes = flowerRes // Update selected flower
                    SlideUpToDownAnimation = true // Start animation
                }
            )
        }
    }


    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingBottomSheet(
    onDismiss: () -> Unit,
    isSoundOn: Boolean,
    onToggleSound: (Boolean) -> Unit,
    onSoundSelected: (Int) -> Unit,
    onBackgroundChange: (Int) -> Unit,
    onImageSelected: (Int) -> Unit,
    onFlowerSelected: (Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var selectedFlower by remember { mutableStateOf<Int?>(null) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp)
            )

            // Toggle Sound
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Om Sound", fontSize = 16.sp)
                Switch(
                    checked = isSoundOn,
                    onCheckedChange = { onToggleSound(it) }
                )
            }

            // Sound Options
            Text(
                text = "Select Music",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            val soundOptions = mapOf(
                "Pooja" to R.raw.bell,
                "Mathalam" to R.raw.temple_aarti_sound_with_sankh,
                "Sangu" to R.raw.traditional_sangu,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                soundOptions.forEach { (label, soundRes) ->
                    Button(
                        onClick = { onSoundSelected(soundRes)
                            onDismiss() },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = label, fontSize = 14.sp)
                    }
                }
            }

            // Background Options
            Text(
                text = "Select Background",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            val backgroundOptions = listOf(
                R.drawable.poojacarner_background6,
                R.drawable.poojacarner_background5,
                R.drawable.poojacarner_background4,
                R.drawable.poojacarner_background3,
                R.drawable.poojacarner_background2,
                R.drawable.poojacarner_background1,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                backgroundOptions.forEach { backgroundRes ->
                    Image(
                        painter = painterResource(id = backgroundRes),
                        contentDescription = "Background Option",
                        modifier = Modifier
                            .width(50.dp).height(60.dp)
                            .clickable {
                                onBackgroundChange(backgroundRes)
                                onDismiss()
                            }
                    )

                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            //select god
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select God Image",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp)            )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    val imageList = listOf(
                        R.drawable.vinayagar_pooja,
                        R.drawable.iyappan,
                        R.drawable.saraswathi,
                        R.drawable.pooja_carner_lakshimi,
                        R.drawable.murugan_pooja,
                        R.drawable.perumaljpg,
                        R.drawable.sivan_new,
                        R.drawable.amman1
                    )

                    items(imageList.size) { index ->
                        val imageRes = imageList[index]
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Black, CircleShape) // Add a circular border
                                .clickable {
                                    onImageSelected(imageRes)
                                    onDismiss()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = "Image $index",
                                modifier = Modifier
                                    .width(50.dp).height(50.dp)
                                //.clip(CircleShape) // Ensure the image is circular
                            )
                        }
                    }

                }
            }
            Spacer(modifier = Modifier.width(10.dp))

            // Flower Selection
            Text(
                text = "Select Flowers",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            val flowerOptions = listOf(
                R.drawable.poojacarnerflower1,
                R.drawable.poojacarnerflower2,
                R.drawable.poojacarnerflower3,
                R.drawable.poojacarnerflower4,
                R.drawable.poojacarnerflower5
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                flowerOptions.forEach { flowerRes ->
                    Image(
                        painter = painterResource(id = flowerRes),
                        contentDescription = "Flower Option",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape) // Clip the shape to a circle
                            .border(2.dp, Color.Black, CircleShape) // Add a circular border
                            .clickable {
                                selectedFlower = flowerRes
                                println("selectedFlowerbottomsheet == $selectedFlower") // Debug log
                                onFlowerSelected(flowerRes) // Call the callback with the selected flower
                                onDismiss() // Dismiss the bottom sheet
                            }
                    )
                }
            }


        }
    }


    // Trigger the flower animation if a flower is selected
    selectedFlower?.let {
        FlowerAnimation(imageResId = it) {
            selectedFlower = null // Reset after animation ends
        }
    }

}

@Composable
fun FlowerAnimation(
    imageResId: Int,
    onAnimationEnd: () -> Unit
) {
    val offsetY = remember { Animatable(-500f) }

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = 2000f,
            animationSpec = tween(
                durationMillis = 3000,
                easing = LinearEasing
            )
        )
        onAnimationEnd() // Trigger when animation ends
    }

    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Animated Flower",
            modifier = Modifier
                .offset { IntOffset(0, offsetY.value.toInt()) }
                .size(64.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageRowWithModalBottomSheet(
    onDismiss: () -> Unit,
    onImageSelected: (Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select an Image",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp)            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                val imageList = listOf(
                    R.drawable.vinayagar_pooja,
                    R.drawable.iyappan,
                    R.drawable.saraswathi,
                    R.drawable.pooja_carner_lakshimi,
                    R.drawable.murugan_pooja,
                    R.drawable.perumaljpg,
                    R.drawable.sivan_new,
                    R.drawable.amman1
                )

                items(imageList.size) { index ->
                    val imageRes = imageList[index]
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Black, CircleShape) // Add a circular border
                            .clickable {
                                onImageSelected(imageRes)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Image $index",
                            modifier = Modifier
                                .fillMaxSize()
                            //.clip(CircleShape) // Ensure the image is circular
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun SlideUpToDownImageAnimation(
    selectedFlower: Int, // Use the selected flower resource ID
    onAnimationEnd: () -> Unit
) {
    val numberOfRows = 3
    val numberOfFlowersPerRow = 7
    val imageResId = selectedFlower

    // Track animation completion
    val animationCompletionCounter = remember { mutableStateOf(0) }
    val totalAnimations = numberOfRows * numberOfFlowersPerRow

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        repeat(numberOfRows) { rowIndex ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(numberOfFlowersPerRow) { index ->
                    SingleFlowerAnimation(
                        imageResId = imageResId, // Use the selected flower
                        delayTimeMillis = calculateRowDelay(rowIndex) + calculateDelay(index),
                        onAnimationEnd = {
                            animationCompletionCounter.value++
                            if (animationCompletionCounter.value == totalAnimations) {
                                onAnimationEnd() // Notify when all animations complete
                            }
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun SingleFlowerAnimation(
    imageResId: Int,
    delayTimeMillis: Int,
    onAnimationEnd: () -> Unit
) {
    val offsetY = remember { Animatable(-500f) }

    LaunchedEffect(Unit) {
        delay(delayTimeMillis.toLong())
        offsetY.animateTo(
            targetValue = 2000f,
            animationSpec = tween(
                durationMillis = 5000,
                easing = LinearEasing
            )
        )
        onAnimationEnd() // Call when animation ends
    }

    Box(
        Modifier
            .size(50.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Animated Flower",
            modifier = Modifier
                .offset { IntOffset(0, offsetY.value.toInt()) }
                .size(50.dp)
        )
    }
}

fun calculateRowDelay(rowIndex: Int): Int {
    return rowIndex * 1800
}

fun calculateDelay(index: Int): Int {
    return when (index) {
        0 -> 0
        1 -> 400
        2 -> 1100
        3 -> 400
        4 -> 900
        5 -> 100
        6 -> 400
        else -> 4000 + (index - 3) * 1000
    }
}
