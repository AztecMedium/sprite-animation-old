import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.lang.Math;

public class Main extends Application{
    private static final int TOLERANCE_THRESHOLD = 0xff;
    private final Image IMAGE = new Image(getClass().getResourceAsStream("Snow.gif"));
    private final Image STATICIMAGE = new Image(getClass().getResourceAsStream("Tree.gif"));
    private final Image BACKGROUNDIMAGE = new Image(getClass().getResourceAsStream("background.jpg"));
    private static final int COLUMNS = 4;
    private static final int COUNT = 4;
    private static final int OFFSET_X = 0;
    private static final int OFFSET_Y = 0;
    private static final int WIDTH = 61;
    private static final int HEIGHT = 79;
    private static final int StaticWidth = 500;
    private static final int StaticHeight = 500;

    private Image makeTransparent(Image inputImage) {
        int W = (int) inputImage.getWidth();
        int H = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                int argb = reader.getArgb(x, y);

                int r = (argb >> 16) & 0xff;
                int g = (argb >> 8) & 0xff;
                int b = argb & 0xff;

                if (r >= TOLERANCE_THRESHOLD
                        && g >= TOLERANCE_THRESHOLD
                        && b >= TOLERANCE_THRESHOLD) {
                    argb &= 0x00ffffFF;
                }

                writer.setArgb(x, y, argb);
            }
        }

        return outputImage;
    }
    @Override
    public void start (Stage primaryStage){
        final Image DynamicResampledImage = makeTransparent(IMAGE);
        final ImageView DynamicResampledImageView = new ImageView(DynamicResampledImage);
        final Image StaticResampledImage = makeTransparent(STATICIMAGE);
        final ImageView StaticResampledImageView = new ImageView(StaticResampledImage);
        final Image BackGroundImage = makeTransparent(BACKGROUNDIMAGE);
        final ImageView BackGroundImageView = new ImageView((BackGroundImage));
        final ImageView St1 = new ImageView(StaticResampledImage);
        final ImageView D1V = new ImageView(DynamicResampledImage);
        final ImageView D2V = new ImageView(DynamicResampledImage);
        final ImageView D3V = new ImageView(DynamicResampledImage);
        final ImageView D4V = new ImageView(DynamicResampledImage);
        St1.setViewport(new Rectangle2D(-250,-250,StaticWidth,StaticHeight));
        StaticResampledImageView.setViewport(new Rectangle2D(0,-200,StaticWidth,StaticHeight));
        DynamicResampledImageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        BackGroundImageView.setViewport((new Rectangle2D(0,0,500,500)));
        D1V.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        Polyline polyline = new Polyline();
        polyline.getPoints().addAll((new Double[]{
                Math.random()*500, -50.0,
                Math.random()*500, 530.0}));
        PathTransition transition = new PathTransition();
        transition.setNode(DynamicResampledImageView);
        transition.setDuration(Duration.seconds(2));
        transition.setPath(polyline);
        final Animation _static = new StaticObject(
                StaticResampledImageView, StaticWidth,StaticHeight
        );
        final Animation st1 = new StaticObject(
                St1, StaticWidth,StaticHeight
        );
        final Animation animation = new SpriteAnimation(
                DynamicResampledImageView,
                Duration.millis(300),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(Animation.INDEFINITE);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.play();
        st1.play();
        _static.play();
        animation.play();

        Polyline polyline1 = new Polyline();
        polyline1.getPoints().addAll((new Double[]{
                Math.random()*500, -100.0,
                Math.random()*500, 530.0}));
        PathTransition tr1 = new PathTransition();
        tr1.setNode(D1V);
        tr1.setDuration(Duration.seconds(2));
        tr1.setPath(polyline1);

        final Animation an1 = new SpriteAnimation(
                D1V,
                Duration.millis(250),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );

        an1.setCycleCount(Animation.INDEFINITE);
        tr1.setCycleCount(PathTransition.INDEFINITE);
        tr1.play();

        an1.play();
        D2V.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        Polyline polyline2 = new Polyline();
        polyline2.getPoints().addAll((new Double[]{
                Math.random()*500, -130.0,
                Math.random()*500, 530.0}));
        PathTransition tr2 = new PathTransition();
        tr2.setNode(D2V);
        tr2.setDuration(Duration.seconds(4.0));
        tr2.setPath(polyline2);
        final Animation an2 = new SpriteAnimation(
                D2V,
                Duration.millis(200),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        an2.setCycleCount(Animation.INDEFINITE);
        tr2.setCycleCount(PathTransition.INDEFINITE);
        tr2.play();
        an2.play();
        D3V.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        Polyline polyline3 = new Polyline();
        polyline3.getPoints().addAll((new Double[]{
                Math.random()*500, -200.0,
                Math.random()*500, 530.0}));
        PathTransition tr3 = new PathTransition();
        tr3.setNode(D3V);
        tr3.setDuration(Duration.seconds(3.2));
        tr3.setPath(polyline3);
        final Animation an3 = new SpriteAnimation(
                D3V,
                Duration.millis(190),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        an3.setCycleCount(Animation.INDEFINITE);
        tr3.setCycleCount(PathTransition.INDEFINITE);
        tr3.play();
        an3.play();
        D4V.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        Polyline polyline4 = new Polyline();
        polyline4.getPoints().addAll((new Double[]{
                Math.random()*500, -300.0,
                Math.random()*500, 530.0}));
        PathTransition tr4 = new PathTransition();
        tr4.setNode(D4V);
        tr4.setDuration(Duration.seconds(4.3));
        tr4.setPath(polyline4);
        final Animation an4 = new SpriteAnimation(
                D4V,
                Duration.millis(274),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        an4.setCycleCount(Animation.INDEFINITE);
        tr4.setCycleCount(PathTransition.INDEFINITE);
        tr4.play();
        an4.play();
        primaryStage.setScene(new Scene(new Group(BackGroundImageView,StaticResampledImageView,DynamicResampledImageView,D1V,St1,D2V,D3V,D4V),500,500));
        primaryStage.show();
    }
    public static void main (String[] args){
        launch(args);
    }
    private class SpriteAnimation extends Transition{
        private final ImageView imageView;
        private final int count;
        private final int columns;
        private final int offsetX;
        private final int offsetY;
        private final int width;
        private final int height;

        SpriteAnimation(
                ImageView imageView,
                Duration duration,
                int count, int columns,
                int offsetX, int offsetY,
                int width, int height){
            this.imageView = imageView;
            this.columns = columns;
            this.count = count;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.width = width;
            this.height = height;
            setCycleDuration(duration);
        }
        protected void interpolate(double k){
            final int index = Math.min((int) Math.floor(k * count), count);
            final int x = (index % columns)* width + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x,y,width,height));
        }
    }
    private class StaticObject extends Transition{
        private final ImageView imageView;
        private final int _static_width;
        private final int _static_height;

        StaticObject(
                ImageView imageView,
                int _static_width, int _static_height){
            this.imageView = imageView;
            this._static_height = _static_height;
            this._static_width = _static_width;
        }
        protected void interpolate(double k){
            imageView.setViewport(new Rectangle2D(0,0,_static_width,_static_height));
        }
    }
}