package fstm.projet.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import fstm.projet.model.bo.*;
import fstm.projet.view.Diagonisation;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;


public class Diagnostic_CTR {

    private static final String host = "172.17.36.160";
    private static final int port = 6000;

    public Diagnostic_CTR() {


    }

    public static void charger_dia(Client c, double temperature, Region r) throws IOException, ClassNotFoundException {

        Diagonisation fram1 = new Diagonisation(c, temperature, r);

        fram1.setVisible(true);
    }

    public static void diagoniser(Client c, Vector<Symptoms> sys, Vector<Maladie_chronique> mal, double temperature, Region r) throws IOException, ClassNotFoundException {
        // c.setMaladies(mal);
        Socketinter socketinter = new Socketinter(sys, c, temperature, r, mal);
        System.out.println(c.getCmptCompte().getEmail());
        Socket socket = new Socket(host, port);
        System.out.println("Connected.................");

        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(socketinter);
        System.out.println("Sent to server.................");
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        String obj1 = (String) ois.readObject();

        JOptionPane.showMessageDialog(null, obj1, "Diagnostic", JOptionPane.INFORMATION_MESSAGE);
    }

    public static ArrayList<Symptoms> afficheSy() throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, port);
        System.out.println("Connected.................");

        Symptoms n = new Symptoms();
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(n);

        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        return (ArrayList<Symptoms>) ois.readObject();

    }

    public static ArrayList<Region> afficheRe() throws IOException, ClassNotFoundException {
        Region obj2 = new Region();
        Socket socket = new Socket(host, port);
        System.out.println("Connected.................");

        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(obj2);

        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        return (ArrayList<Region>) ois.readObject();
	/*DAORegion deDaoRegion=new DAORegion();
	return deDaoRegion.retreiveR();*/
    }

    public static void insertClient(String nom, String prenom, Boolean sexe, Calendar date, String email, String password) throws IOException, ClassNotFoundException {
        Compte cmpCompte = new Compte(email, password);
        Client cli = new Client(nom, prenom, sexe, date, cmpCompte);
        Socket socket = new Socket(host, port);
        System.out.println("Connected.................");

        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);

        oos.writeObject(cli);

    }

    public static void updateClient(Client c, double temp, Region reg) throws IOException, ClassNotFoundException {

        charger_dia(c, temp, reg);
    }

    public static Client authClient(String email, String passString) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, port);
        System.out.println("Connected.................");

        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        SocketInscription n = new SocketInscription(email, passString);
        oos.writeObject(n);

        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        return (Client) ois.readObject();
		/*DAOClient daoClient=new DAOClient();
return daoClient.Authentification(email, passString);*/

    }

    public static void rempliTable(DefaultTableModel model, Client c) {
        for (Diagnostic diag : c.getDiagnostics()) {
            model.addRow(new String[]{diag.getDate().toString(), "" + diag.get_possi_presence()});
            System.out.println(diag);
        }

    }

    public static void Telecharger_doc(String ext, Client c, int idr) {
        if ("pdf".equals(ext)) {
            Document document = new Document();
            System.out.println(c.getDiagnostics().get(idr - 1));
            try {
                PdfWriter.getInstance(document, new FileOutputStream(c.getPrenom() + "-" + c.getNom() + "." + ext));
            } catch (FileNotFoundException | DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            font.setSize(40);
            font.setStyle(Font.BOLD);
            font.setColor(BaseColor.GRAY);
            Chunk chunk = new Chunk("Diagnostic", font);

            try {
                document.add(chunk);

                document.add(new Paragraph("\n" + c.getDiagnostics().get(idr - 1).getDate().toString()));
                // Informations de client

                font.setSize(15);
                font.setStyle(Font.BOLD);
                font.setColor(BaseColor.BLUE);
                document.add(new Chunk("\nInformations du client ", font));
                document.add(new Paragraph(" Nom :" + c.getNom() + "\n Prenom : " + c.getPrenom() + " \n Age : " + c.getage()));
                document.add(new Chunk("\nInformations du diagnostic ", font));
                document.add(new Paragraph("\n Temperature :" + c.getDiagnostics().get(idr - 1).getTemperature() + "\n Region :" + c.getDiagnostics().get(idr - 1).getRegion().getNom_region()));
                font.setSize(10);
                font.setStyle(Font.BOLD);
                font.setColor(BaseColor.LIGHT_GRAY);
                document.add(new Chunk("\nliste des maladies chronique\n ", font));
                StringBuilder symString = new StringBuilder("  ");
                for (Maladie_chronique s : c.getDiagnostics().get(idr - 1).getMaladies()) {
                    System.out.println(s.getNom());
                    symString.append("     ").append(s.getNom());
                }
                document.add(new Paragraph(symString.toString()));
                document.add(new Chunk("\nliste de symptom\n ", font));
                symString = new StringBuilder("  ");
                for (Symptoms s : c.getDiagnostics().get(idr - 1).getMysymtoms()) {
                    symString.append("     ").append(s.designation);
                }
                document.add(new Paragraph(symString.toString()));
                font.setSize(15);
                font.setStyle(Font.BOLD);
                font.setColor(BaseColor.BLUE);
                document.add(new Chunk("\n Resultat du diagnostic\n ", font));
                document.add(new Paragraph("\n resultat :" + c.getDiagnostics().get(idr - 1).getResultat()));
            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            document.close();
        } else {
            XWPFDocument document = new XWPFDocument();
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(new File("nouveaudoc.docx"));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            // Diagnostic
            run.setBold(true);
            run.setFontSize(40);
            run.setColor("808080"); //Set Color
            run.setText("Diagnostic");
            //date
            XWPFParagraph paragraph2 = document.createParagraph();
            XWPFRun date = paragraph2.createRun();
            date.setFontSize(18);
            date.setText("20202-02-18");
            // informations du client
            XWPFParagraph paragraph3 = document.createParagraph();
            XWPFRun info = paragraph3.createRun();
            info.setFontSize(25);
            info.setColor("0000FF");
            info.setText("Informations du client ");
            XWPFParagraph paragraph4 = document.createParagraph();
            XWPFRun nom = paragraph4.createRun();
            nom.setFontSize(14);
            nom.setText(" Nom :" + c.getNom());
            paragraph4 = document.createParagraph();
            XWPFRun prenom = paragraph4.createRun();
            prenom.setFontSize(14);
            prenom.setText(" Prenom : " + c.getPrenom());
            paragraph4 = document.createParagraph();
            XWPFRun age = paragraph4.createRun();
            age.setFontSize(14);
            age.setText("  Age : " + c.getage());
            XWPFParagraph paragraph5 = document.createParagraph();
            XWPFRun info1 = paragraph5.createRun();
            info1.setFontSize(25);
            info1.setColor("0000FF");
            info1.setText("Informations du diagnostic ");
            XWPFParagraph paragraph6 = document.createParagraph();
            XWPFRun temp = paragraph6.createRun();
            temp.setFontSize(18);
            temp.setText("Temperature :" + c.getDiagnostics().get(idr - 1).getTemperature());
            paragraph6 = document.createParagraph();
            XWPFRun region = paragraph6.createRun();
            region.setFontSize(18);
            region.setText(" Region :" + c.getDiagnostics().get(idr - 1).getRegion().getNom_region());
            XWPFParagraph paragraph7 = document.createParagraph();
            XWPFRun info2 = paragraph7.createRun();
            info2.setFontSize(18);
            info2.setColor("FFE4E1");
            info2.setText("liste des maladie chronique ");
            XWPFParagraph paragraph8 = document.createParagraph();
            XWPFRun mal = paragraph8.createRun();
            mal.setFontSize(14);
            StringBuilder symString = new StringBuilder("  ");
            for (Maladie_chronique s : c.getDiagnostics().get(idr - 1).getMaladies()) {
                System.out.println(s.getNom());
                symString.append("     ").append(s.getNom());
            }
            mal.setText(symString.toString());
            XWPFParagraph paragraph9 = document.createParagraph();
            XWPFRun info3 = paragraph9.createRun();
            info3.setFontSize(18);
            info3.setColor("FFE4E1");
            info3.setText("liste des symptoms ");
            XWPFParagraph paragraph10 = document.createParagraph();
            XWPFRun sym = paragraph10.createRun();
            sym.setFontSize(14);
            symString = new StringBuilder("  ");
            for (Symptoms s : c.getDiagnostics().get(idr - 1).getMysymtoms()) {
                symString.append("     ").append(s.designation);
            }
            sym.setText(symString.toString());
            paragraph = document.createParagraph();
            run = paragraph.createRun();
            // Diagnostic
            run.setBold(true);
            run.setFontSize(40);
            run.setColor("808080"); //Set Color
            run.setText("Resultat : " + c.getDiagnostics().get(idr - 1).getResultat());
            try {
                document.write(out);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public static void rempliTableRegion(DefaultTableModel model, JTable t) {
        try {

            for (Region reg : afficheRe()) {
                model.addRow(new String[]{reg.getNom_region(), reg.getNombre_contamines() + "", reg.getNombre_deces() + "", reg.getNombre_geuris() + ""});
                System.out.println(reg);
            }

            //t.getColumnModel().getColumn(1).setCellRenderer(colouringTable);


        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

