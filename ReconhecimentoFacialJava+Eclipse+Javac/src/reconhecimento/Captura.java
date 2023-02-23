package reconhecimento;

import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;

import java.awt.event.KeyEvent;
import java.util.Scanner;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.opencv.imgproc.Imgproc;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.global.opencv_objdetect;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgproc.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_imgcodecs;






public class Captura {
     public static void main(String arg []) throws Exception, InterruptedException {
    	KeyEvent tecla = null; 
    	OpenCVFrameConverter.ToMat converteMat = new OpenCVFrameConverter.ToMat();
        OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
        camera.start();
        
        CascadeClassifier detectorFace = new CascadeClassifier("src/recursos/007 haarcascade-frontalface-alt.xml");
        
        
        CanvasFrame cFrame = new CanvasFrame("Preview",CanvasFrame.getDefaultGamma() / camera.getGamma() );
        Frame frameCapturado = null;
        
        Mat imagemColorida = new Mat();
        
        int numeroAmostras = 25;
    	int amostra = 1;
    	
    	System.out.println("digite seu id: " );
    	Scanner cadastro = new Scanner(System.in);
    	int idPessoa = cadastro.nextInt();
        
        while ((frameCapturado = camera.grab()) != null) {
        	imagemColorida = converteMat.convert(frameCapturado);
        	Mat imagemCinza = new Mat();
        	       	
        	
        	cvtColor(imagemColorida, imagemCinza, opencv_imgproc.COLOR_BGRA2GRAY);
        	
        	RectVector facesDetectadas = new RectVector();
        	detectorFace.detectMultiScale(imagemCinza, facesDetectadas, 1.1, 1, 0, new Size (150,150), new Size(500,500) );
        	
        	if (tecla == null ) {
        		tecla = cFrame.waitKey (5);
        	}
        	
        	
        	for (int i=0; i < facesDetectadas.size(); i++){
        		Rect dadosFace = facesDetectadas.get(0);
        		opencv_imgproc.rectangle (imagemColorida, dadosFace, new Scalar(0, 0, 255, 0) );
        		
        		//Mat faceCapturada = new Mat (imagemCinza,dadosFace);
        		//resize (faceCapturada,faceCapturada, new Size(160,160));
        		
        		Mat faceCapturada = new Mat(imagemCinza, dadosFace);
        		opencv_imgproc.resize(faceCapturada, faceCapturada, new Size(160, 160));
        		
        		if (tecla == null ) {
            		tecla = cFrame.waitKey (5);
            	}
        		
        		//if (tecla == null) {
        		//	if (tecla.getKeyChar() == 'q') {
        		//		if (amostra <= numeroAmostras ) {
        			//		imwrite ("src\\fotos\\pessoa." + idPessoa + "." +amostra + "jpg", faceCapturada);
        				//	System.out.println ("Foto "+ amostra + "capturadas\n");
        				//	amostra++;
        		
        		if (tecla !=null) {
        		    if (tecla.getKeyChar() == 'q') {
        		        if (amostra <= numeroAmostras ) {
        		            opencv_imgcodecs.imwrite("src\\fotos\\pessoa." + idPessoa + "." + amostra + ".jpg", faceCapturada);
        		            System.out.println("Foto "+ amostra + "capturadas\n");
        		            amostra++;
        					
        		            
        				}
        			}
        		    
        		    tecla = null;
        		    
        		}
        		
        		
        		
        	}
        	
        	if (tecla == null ) {
        		tecla = cFrame.waitKey (20);
        	}
        	
        	if (cFrame.isVisible()) {
        		cFrame.showImage(frameCapturado);
        	}
        	
        	if  (cFrame.isVisible()) {
        		cFrame.showImage(frameCapturado);
        	}
        	
        	if(amostra > numeroAmostras ) {
        		break;
        	}
        	
        }
        cFrame.dispose();
        camera.stop();
     } 
     
     
}
