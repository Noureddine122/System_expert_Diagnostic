package fstm.projet.model.bo;

import java.io.Serializable;

public class DroolsTest implements Serializable {
	

    public double Start_Rules(Diagnostic d) {
    	
    	return d.traiter();
    	
    }
    public boolean isEnvoy(Docteur doc)
    {
    	
    	return doc.isRemplie();
    }
    
  
}
