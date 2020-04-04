import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;

public class Main extends Application{
    private static final int TOLERANCE_THRESHOLD = 0xff;
    private final Image IMAGE = new Image(getClass().getResourceAsStream("Snow.gif"));
    private final Image STATICIMAGE = new Image(getClass().getResourceAsStream("Tree.gif"));
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
        Pane root = new Pane();

        StackPane holder = new StackPane();
        Canvas canvas = new Canvas(500,  500);

        holder.getChildren().add(canvas);
        root.getChildren().add(holder);

        holder.setStyle("-fx-background-color: darkcyan");
        final Image DynamicResampledImage = makeTransparent(IMAGE);
        final ImageView DynamicResampledImageView = new ImageView(DynamicResampledImage);
        final Image StaticResampledImage = makeTransparent(STATICIMAGE);
        final ImageView StaticResampledImageView = new ImageView(StaticResampledImage);
        StaticResampledImageView.setViewport(new Rectangle2D(-100,0,StaticWidth,StaticHeight));
        DynamicResampledImageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        Path path = new Path();
        path.getElements().add(new MoveTo(100, 100));
        path.getElements().add(new LineTo(0, 0));
        final Animation _static = new StaticObject(
                StaticResampledImageView, StaticWidth,StaticHeight
        );
        final Animation animation = new SpriteAnimation(
                DynamicResampledImageView,
                Duration.millis(500),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(Animation.INDEFINITE);
        _static.play();
        animation.play();

        primaryStage.setScene(new Scene(new Group(root,StaticResampledImageView,DynamicResampledImageView),500,500));
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