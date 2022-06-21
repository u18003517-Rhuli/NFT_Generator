import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.util.ArrayList;

public class NFT_Generator {

    public static void main(String[] args) throws IOException {

        Networks network = new Networks();

        int testNum = getTestNumber();

        //Image image1 = new ImageIcon(NFT_Generator.class.getClass().getResource("Images/elephant.jpg")).getImage();
        //Image image2 = new ImageIcon(NFT_Generator.class.getClass().getResource("Images/words.png")).getImage();
        //image1.getWidth();
        //Class<? extends Class> hey = NFT_Generator.class.getClass();


        ImageIcon image1 = new ImageIcon("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/resources/Images/elephant.jpg");
        ImageIcon image2 = new ImageIcon("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/resources/Images/words.png");

        //ImageIcon image1 = new ImageIcon("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/resources/Images/Tame_Impala-Currents.png");
        //ImageIcon image2 = new ImageIcon("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/resources/Images/slow_rush.jpg");


        ImageObserver comp = new JComponent() {
            private static final long serialVersionUID = 1L;
        };

        //int w = image1.getIconWidth() + image2.getIconWidth();
        int w = Math.max(image1.getIconWidth() , image2.getIconWidth());
        int h = Math.max(image2.getIconHeight(), image2.getIconHeight());

        BufferedImage image = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = image.createGraphics();

        g2.drawImage(image1.getImage(), 0, 0, comp);
        g2.drawImage(image2.getImage(), image1.getIconWidth(), 0, comp);
        g2.dispose();

        ArrayList<BufferedImage> imagesToMerge = scaleImage(image1, image2, comp);

        BufferedImage mergedImage = mergeImages(imagesToMerge);

        for(int i =0; i < imagesToMerge.size(); i++){
            if(i ==0) {
                File outputfile = new File("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/java/NFT_Image/testImage" +testNum +"_1.jpg");
                File outputfile2 = new File("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/java/NFT_Image/testImage" +testNum +"_1.png");
                ImageIO.write(imagesToMerge.get(i), "jpg", outputfile);
                ImageIO.write(imagesToMerge.get(i), "png", outputfile2);
            }else{
                File outputfile = new File("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/java/NFT_Image/testImage" +testNum +"_2.jpg");
                File outputfile2 = new File("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/java/NFT_Image/testImage" +testNum +"_2.png");
                ImageIO.write(imagesToMerge.get(i), "jpg", outputfile);
                ImageIO.write(imagesToMerge.get(i), "png", outputfile2);
            }
        }
        //return image;
        File outputfile = new File("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/java/NFT_Image/testImage" +testNum +".jpg");
        File outputfile2 = new File("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/java/NFT_Image/testImage" +testNum +".png");
        ImageIO.write(mergedImage, "jpg", outputfile);
        ImageIO.write(mergedImage, "png", outputfile2);

    }

    private static int getTestNumber() {
        int output = -1;

        String fileURL = "C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/java/testNum";

        BufferedReader br =null;
        try {

            br = new BufferedReader(new FileReader(fileURL));

            //StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            /*while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }*/
            //String everything = sb.toString();
            output = Integer.parseInt(line);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        output = output +1;

        try {
            FileWriter writer = new FileWriter(fileURL, false);
            writer.write(String.valueOf(output));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return output;
    }

    private static BufferedImage mergeImages(ArrayList<BufferedImage> imagesToMerge) {

        int imageHeight = imagesToMerge.get(0).getHeight();
        int imageWidth = imagesToMerge.get(0).getWidth();
        System.out.println("check brahh " + imageHeight +" : " + imageWidth);

        BufferedImage mergedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        /**************linear Cut ****************/
        double gradient = (double) imageHeight/ (double) imageWidth;
        //gradient = gradient *-1;

        BufferedImage image1 =  imagesToMerge.get(0);
        BufferedImage image2 =  imagesToMerge.get(1);
        System.out.println(gradient + " check it here");
        for(int x =0; x < imageWidth; x++){
            for(int y =0; y < imageHeight; y++){
                double fValue = gradient * x; // f(x): y = mx + b; BUT b ==0 always [intersect]
                if( y > fValue){ //top half
                    mergedImage.setRGB(x,y,image2.getRGB(x,y));
                }else{ //bottom half
                    mergedImage.setRGB(x,y,image1.getRGB(x,y));
                }
            }
        }

        return  mergedImage;
    }

    private static ArrayList<BufferedImage> scaleImage(ImageIcon image1, ImageIcon image2, ImageObserver comp) {

        int w1 = image1.getImage().getWidth(comp);
        int h1 = image1.getImage().getHeight(comp);
        int ow1 = w1;
        int oh1 = h1;


        BufferedImage originImage1 = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = originImage1.createGraphics();
        g2.drawImage(image1.getImage(), 0, 0, comp);
        g2.dispose();

        int w2 = image2.getImage().getWidth(comp);
        int h2 = image2.getImage().getHeight(comp);
        int ow2 = w2;
        int oh2 = h2;


        BufferedImage originImage2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_ARGB);
        g2 = originImage2.createGraphics();
        g2.drawImage(image2.getImage(), 0, 0, comp);
        g2.dispose();


        while(w1 != w2){
            if( (w1+1) == w2 ){
                w1 = w2;
                //break;
            }else if((w2+1) == w1){
                w2 = w1;
                //break;
            }else if(w1 > w2 ){
                w1 = w1 -1;
                w2 = w2 +1;
            }else if(w2 > w1 ){
                w2 = w2 -1;
                w1 = w1 +1;
            }
            System.out.println("w");
        }

        while(h1 != h2){
            if( (h1+1) == h2 ){
                h1 = h2;
            }else if((h2+1) == h1){
                h2 = h1;
            }else if(h1 > h2){
                h1 = h1 -1;
                h2 = h2 +1;
            }else if(h2 > h1){
                h2 = h2 -1;
                h1 = h1 +1;
            }
            System.out.println("h");
        }

        int scaledHeight = h1 ;
        int scaledWidth =w1;

        ArrayList<BufferedImage> scaledImages = new ArrayList<>();

        //ONE
        BufferedImage scaledImage = new BufferedImage(scaledWidth ,scaledHeight, BufferedImage.TYPE_INT_ARGB);

        double offsetWidth = (double) scaledWidth / (double)ow1;
        double offsetHeight = (double) scaledHeight / (double)oh1;
        System.out.println(offsetWidth + " check die offset "+ offsetHeight);

        AffineTransform at = AffineTransform.getScaleInstance(offsetWidth, offsetHeight);
        AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        scaledImage = ato.filter(originImage1, scaledImage);

        scaledImages.add(scaledImage);

        //TWO
        scaledImage = new BufferedImage(scaledWidth ,scaledHeight, BufferedImage.TYPE_INT_ARGB);

        offsetWidth = (double) scaledWidth / (double)ow2;
        offsetHeight = (double) scaledHeight / (double)oh2;
        System.out.println(offsetWidth + " check die offset2 "+ offsetHeight);

        AffineTransform at2 = AffineTransform.getScaleInstance(offsetWidth, offsetHeight);
        AffineTransformOp ato2 = new AffineTransformOp(at2, AffineTransformOp.TYPE_BICUBIC);
        scaledImage = ato2.filter(originImage2, scaledImage);

        scaledImages.add(scaledImage);

        /*dbi = new BufferedImage(dWidth, dHeight, imageType);
        Graphics2D g = dbi.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
        g.drawRenderedImage(sbi, at);*/



        //final Scale scaler = new Scale(2);
        //BufferedImage scaledImage1= scaler.apply(image);

        scaledImages.add(scaledImage);

        return scaledImages;
    }



    static void paint(){
        //Will paint a new image or on an existing image
        //TODO can try out paint algorith here


    }




}
