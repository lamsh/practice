package dragdrop;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DragDrop extends Application {

    // まずはオブジェクトを作成
    final private Text source = new Text(50, 100, "DRAG ME");
    final private Text target = new Text(250, 100, "DROP HERE");

    @Override
    public void start(Stage stage) throws Exception {        
        // ジェスチャーソースのDRAG_DETECTEDイベントを処理する
        source.setOnDragDetected(event -> dragDrop(event));
        
        // ジェスチャーターゲットのDRAG_OVERイベントを処理
        target.setOnDragOver(event -> dragOver(event));
        
        // ドラッグドロップの視覚効果を設定
        target.setOnDragEntered(event -> dragEntered(event));
        target.setOnDragExited(event -> dragExited(event));
        
        // ドロップ後の処理を設定
        target.setOnDragDropped(event -> dragDropped(event));
        
        // ドラッグドロップ完了時のドロップ元の設定
        source.setOnDragDone(event -> dragDone(event));
        
        // その他レイアウトなどの設定
        stage.setTitle("Hello Drag and Drop");
        
        Group root = new Group();
        Scene scene = new Scene(root, 400, 200);          
        scene.setFill(Color.LIGHTGREEN);
        
        source.setScaleX(2.0);
        source.setScaleY(2.0);
        target.setScaleX(2.0);
        target.setScaleY(2.0);
                
        root.getChildren().addAll(source, target);
        stage.setScene(scene);
        stage.show();
    }
    
    public void dragDrop(MouseEvent event){
        // 転送モードを指定して，startDragAndDropメソッドでドラッグ・ドロップを開始
        // COPY, MOVE, LINK, ANY, COPY_OR_MOVE, NONE
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        
        // クリップボードを作成して格納
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getText());
        db.setContent(content);
        
        event.consume();        
    }
    
    public void dragOver(DragEvent event){
        // 現在のダッシュボードのデータ型から受け入れるか判定
        // 最初のevent.getGesutureSourceでオブジェクトの判定
        // getDragboard()でダッシュボードに格納されているデータにアクセス
        if (event.getGestureSource() != target &&
                event.getDragboard().hasString()){
            // ドロップの受け入れ関数を実行
            // 受け入れる転送モードを指定する
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }
    
    public void dragEntered(DragEvent event){
        // ドラッグ対象範囲内に入った視覚効果を設定
        if (event.getGestureSource() != target &&
                event.getDragboard().hasString())
        {
            target.setFill(Color.GREEN);
        }
        event.consume();
    }

    public void dragExited(DragEvent event){
        // ドラッグ対象範囲外に入った視覚効果を設定
        target.setFill(Color.BLACK);
        event.consume();
    }

    public void dragDropped(DragEvent event){
        // ドロップ時の処理を設定
        Dragboard db = event.getDragboard();
        final boolean HAS_DB_STRING = db.hasString();
        if (HAS_DB_STRING){
            target.setText(db.getString());
        }
        
        event.setDropCompleted(HAS_DB_STRING);
        event.consume();        
    }

    public void dragDone(DragEvent event){
        // ドロップ後のドロップ元の処理を設定
        if (event.getTransferMode() == TransferMode.MOVE){
            source.setText("DROP FINISH");
        }        
        event.consume();        
    }
}