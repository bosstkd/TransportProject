package Etat;
import interfaces.FirstPg;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class etat_pdf_rech {

	/**
	 * @param args
	 */
	
	String fl_chemin = "";
	int save = 0;	
	
	
	public etat_pdf_rech(final Vector titre,final Vector contenu,int index, String tit) {
		
		
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("C:/JTrans/Enregistrement"));
		chooser.setSelectedFile(new File(tit.replaceAll("/", "").replaceAll("°", "").replaceAll(":", "").replaceAll(" ", "")));

		int ret = chooser.showSaveDialog(null);
		if(ret == JFileChooser.APPROVE_OPTION){

		//-------------------------------------------------------------------			
					 fl_chemin = chooser.getSelectedFile().toString()+"";
				     System.out.println(fl_chemin);
			// TODO Auto-generated method stub
			Document document = new Document(PageSize.A4, 20, 20, 20, 20);
			
			try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fl_chemin+".pdf"));
				document.open();
				
				
				Paragraph title1 = new Paragraph(" ", 
						FontFactory.getFont(FontFactory.HELVETICA, 
						   1, Font.BOLDITALIC, new CMYKColor(0, 255, 255,17)));
						Chapter chapter1 = new Chapter(title1, 1);
						chapter1.setNumberDepth(0);
				Paragraph title11 = new Paragraph(" ", 
					       FontFactory.getFont(FontFactory.HELVETICA, 1, Font.ITALIC, 
					       new CMYKColor(0, 255, 255,17)));
				
				
				
				
				  Anchor anchorTarget = new Anchor("                                                                                                                                   Le: "+new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				      anchorTarget.setName("BackToTop");
				      Paragraph paragraph1 = new Paragraph();
				      paragraph1.setSpacingBefore(50);
				      paragraph1.add(anchorTarget);
				      try {
						document.add(paragraph1);
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				Section section1 = chapter1.addSection(title11);
				
				
				
				
				float taille = contenu.size() / titre.size();
				
				taille = taille / 30;
				
				int boucle = (int) taille;
				
				if(taille - boucle > 0) boucle = boucle + 1;
				
				if(boucle == 0)boucle = 1;
				
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				
				for(int i = 0; i<boucle; i++){
					//*****************
					en_tete( document, section1,tit, boucle);
					//*****************   
					tableau( section1,titre, contenu, index, taille);
					//*****************	   	 
	         		
				     section1.add(new Paragraph("Signature et cachet: "));

					      if(i+1<boucle)for (int x = 0; x<5; x++){
					    	  											section1.add(new Paragraph("    ",new Font(FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,11, Font.NORMAL, new CMYKColor(255, 255, 255, 255)))));
						   										 }
				}
				
				
				//--------------------------------------------------		

					document.add(section1);

			document.close();
		    JOptionPane.showMessageDialog(null,"Etat créer avec succés","Information",JOptionPane.INFORMATION_MESSAGE);
		    int conf = JOptionPane.showConfirmDialog(null, "Voulez vous ouvrir le fichier !!?", "confirmation de suppression",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(conf == JOptionPane.OK_OPTION){
				 if (Desktop.isDesktopSupported()) {
		        try {
		            File myFile = new File(fl_chemin+".pdf");
		            Desktop.getDesktop().open(myFile);
		        } catch (IOException ex) {
		            // no application registered for PDFs
		        }
		    }
			}
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    JOptionPane.showMessageDialog(null,"Erreur : "+e,"Erreur",JOptionPane.ERROR_MESSAGE);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    JOptionPane.showMessageDialog(null,"Erreur : "+e,"Erreur",JOptionPane.ERROR_MESSAGE);
			}				
		//-------------------------------------------------------------------			
		}
	}
	
	void en_tete( Document document, Section section1,String tit,int boucle){
		Image image2 = null;
		try {
			  try {
				image2 = Image.getInstance("C:/JTrans/Logo.jpg");
			} catch (BadElementException e) {
				JOptionPane.showMessageDialog(null, "Veillez ajouter un Logo SVP. Erreur : "+e,"Attention",JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			}
			  image2.scaleAbsolute(120f, 80f);
			  image2.setAbsolutePosition(20, 700);
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, "Veillez ajouter un Logo SVP. Erreur : "+e,"Attention",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Veillez ajouter un Logo SVP. Erreur : "+e,"Attention",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}

		String req = "Select NOM, ADRESSE, NRC, NIF, NIS, RIB, TEL, FAX, MAIL from TRANS_INFO_PERSO";
		String adresse = "";
		String NOM = "";
		String NRC = "";
		String NIF = "";
		String NIS = "";
		String RIB = "";
		String TEL_FAX = "";
		String MAIL  = "";
		
		
	
		
		try {
			Statement stm = FirstPg.con.createStatement();
			ResultSet rst = stm.executeQuery(req);
			
			
			
			while(rst.next()){
				
				 NOM = rst.getString(1);
				 adresse = rst.getString(2);
				 NRC = rst.getString(3);
				 NIF = rst.getString(4);
				 NIS = rst.getString(5);
				 RIB = rst.getString(6);
				 TEL_FAX = rst.getString(7)+"/"+rst.getString(8);
				 MAIL  = rst.getString(9);
			}
			
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	      Paragraph someSectionText = new Paragraph(" ");

			section1.add(image2); 
			

			Font fnt = new Font(FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 15, Font.NORMAL, new CMYKColor(255, 255, 255, 255)));
			
						section1.add(someSectionText);
			
			Paragraph du_txt = new Paragraph("                                 "+NOM,fnt);
			

			section1.add(du_txt);
			du_txt = new Paragraph("                                 "+adresse,fnt);
			
			section1.add(du_txt);
			
			
			section1.add(someSectionText);
			section1.add(someSectionText);
			section1.add(someSectionText);
			
			
			String espace = "";
			
			int i_space = 130;
			int taille_font = 9;
			
			for(int n =("Nrc : "+NRC).length(); n < i_space; n++){
				espace = espace + " ";
			}
			section1.add(new Paragraph("Nrc : "+NRC, new Font(FontFactory.getFont(FontFactory.TIMES_ROMAN, taille_font, Font.NORMAL, new CMYKColor(255, 255, 255, 255)))));
			espace = "";
			for(int n = ("Nif : "+NIF).length(); n < i_space; n++){
				espace = espace + " ";
			}
			section1.add(new Paragraph("Nif : "+NIF,new Font(FontFactory.getFont(FontFactory.TIMES_ROMAN, taille_font, Font.NORMAL, new CMYKColor(255, 255, 255, 255)))));
			espace = "";
			for(int n = ("Nis : "+NIS).length(); n < i_space; n++){
				espace = espace + " ";
			}
			section1.add(new Paragraph("Nis : "+NIS,new Font(FontFactory.getFont(FontFactory.TIMES_ROMAN, taille_font, Font.NORMAL, new CMYKColor(255, 255, 255, 255)))));
			espace = "";
			
			section1.add(new Paragraph("RIB : "+RIB+".",new Font(FontFactory.getFont(FontFactory.TIMES_ROMAN, taille_font, Font.NORMAL, new CMYKColor(255, 255, 255, 255)))));
			section1.add(new Paragraph("Tel/Fax : "+TEL_FAX+".   Mail : "+MAIL));
	
			section1.add(someSectionText);
			section1.add(someSectionText);
			
			section1.add(new Paragraph(tit,new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 15, Font.NORMAL, new CMYKColor(255, 255, 255, 255)))));

	}
	
	
	void tableau(Section section1,final Vector titre,final Vector contenu,int taille, float row_){
		PdfPTable t = null;
		
		
		  t = new PdfPTable((int)taille);
		  t.setHorizontalAlignment(0);
		  t.setWidthPercentage(100);
	      t.setSpacingBefore(25);
	      t.setSpacingAfter(25);
	      
	      PdfPCell c1;
	      Font fontH1 = new Font(FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 9, Font.NORMAL, new CMYKColor(255, 255, 255, 255)));

			//--------------------------------------------------		

	        for(int i = 0; i < titre.size(); i++){
	        	c1 = new PdfPCell(new Phrase(titre.elementAt(i).toString(),fontH1));  
			    t.addCell(c1);
	        }
	     
			
			    
			 for(int i = 0; i < contenu.size(); i++){
				 c1 = new PdfPCell(new Phrase(contenu.elementAt(i).toString(),fontH1));  
				  t.addCell(c1);
			 }
			  
			
			   
				section1.add(t);
			   	save = save + 30; 
	}
}
