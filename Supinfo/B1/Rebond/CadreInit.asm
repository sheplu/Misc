org 100h  
        
        
;generation cadre        
MOV AX, 0B800h

MOV DS, AX

MOV [158], 201   

MOV BX, 160

boucle_haut:

 MOV [BX], 205 
 
 ADD BX, 2
 
 CMP BX, 314
 
 JB boucle_haut 

 MOV [BX],187 

                
                
 boucle_cote:       
 
 ADD BX, 2
 
 MOV [BX], 186
 
 ADD BX, 156
 
 MOV [BX], 186
 
 CMP BX, 3790      
 
 JB boucle_cote
        
 ;coin bas1       
 ADD BX, 2       
 MOV [BX], 200 
 ;coin bas2
 ADD BX, 156
 MOV [BX], 188       
 
 
  boucle_bas: 
  
 SUB BX, 2
 
 MOV [BX], 205
 
 CMP BX, 3794
 
 JA boucle_bas

             
;generation point

 
