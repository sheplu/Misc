org 100h  
        
 
; initialisation chrono:
         
  JMP Programme        ; saut au programme principal
  
                 
;//////////////     PROCEDURES     \\\\\\\\\\\\\\                  
                 
   
;  |||   gestion des collisions   ||| 
    
    BD_Coll PROC    ; condition bordure lorsque direction Bas-Droite   
    MOV BX, AX
    ADD BX, 161         ; charactere suivant si continue
    CMP [BX], 0xBAh     ; si c'est une bordure
    JE boucle4          ; on change de direction
    RET
    BD_Coll ENDP
                 
                 
    HD_Coll PROC    ; condition bordure lorsque direction Haut-Droite   
    MOV BX, AX      
    SUB BX, 159         ; charactere suivant si continue        
    CMP [BX], 0xBA      ; si c'est une bordure
    JE  boucle5         ; on change de direction
    RET  
    HD_Coll ENDP  
                
                    
    BG_Coll PROC    ; condition bordure lorsque direction Bas-Gauche
    MOV BX, AX      
    ADD BX, 157         ; charactere suivant si continue            
    CMP [BX], 0xBA      ; si c'est une bordure        
    JE  boucle2         ; on change de direction
    RET
    BG_Coll ENDP
                 
                 
    HG_Coll PROC    ; condition bordure lorsque direction Haut-Gauche
    MOV BX, AX      
    SUB BX, 163         ; charactere suivant si continue             
    CMP [BX], 0xBA      ; si c'est une bordure          
    JE boucle3          ; on change de direction
    RET
    HG_Coll ENDP   
    
;  |||   fin gestion des collisions   |||


;  |||    gestion des angles   ||| 
   
   Angle_HG PROC
   MOV BX, AX           ; on met AX dans BX
   CMP BX, 323          ; on compare BX a la position de l'angle en haut a gauche
   JE  TP               ; si egal on va a TP
   RET                  
   Angle_HG ENDP 
   
   Angle_HD PROC        
   MOV BX, AX           ; on met AX dans BX
   CMP BX, 477          ; on compare BX a la position de l'angle en haut a droite
   JE  TP               ; si egal on va a TP
   RET                  
   Angle_HD ENDP

   Angle_BD PROC
   MOV BX, AX           ; on met AX dans BX
   CMP BX, 3837         ; on compare BX a la position de l'angle en bas a droite
   JE  TP               ; si egal on va a TP
   RET                  
   Angle_BD ENDP
   
   Angle_BG PROC
   MOV BX, AX           ; on met AX dans BX
   CMP BX, 3683         ; on compare BX a la postion en bas a gauche
   JE  TP               ; si egal on va a TP
   RET                  
   Angle_BG ENDP  
   
   
   TP:             ; teleportation !!!!!!!!!
   MOV AX, 1001    ; on deplace le carre
   MOV BX, AX      ; on met AX dans BX
   MOV [BX], 0xDDh ; on affiche un beau violet   
   JMP boucle2     ; on redemarre


;  |||   fin gestion des angles   |||


 

;//////////////    FIN   PROCEDURES     \\\\\\\\\\\\\\ 
 
           
   
   
           
;//////////////     PROGRAMME     \\\\\\\\\\\\\\   
        
    Programme:
    
     
; mode 80*25 
 MOV AX, 0B800h
 MOV DS, AX      
     
       
         
;  |||   generation du cadre   |||      

  
 MOV [160], 201     ; un saut de ligne et placement de l'angle 
 MOV BX, 162        ; avance au charactere suivant
        
           
;generation ligne haute du cadre           
boucle_haut:
 MOV [BX], 205      ; on met une bordure 
 ADD BX, 2          ; on avance au prochain charactere
 CMP BX, 318        ; on compare a 318
 JB boucle_haut     ; si inferieur on recommence
 MOV [BX],187       ; sinon on met l'angle

                
; generation des deux lignes laterales                
 boucle_cote:       
 ADD BX, 2          ; on avance au premier charactere
 MOV [BX], 186      ; on affiche une bordure
 ADD BX, 158        ; on avance de l'autre cote du cadre
 MOV [BX], 186      ; on affiche une bordure
 CMP BX, 3790       ; on compare a 3790 
 JB boucle_cote     ; si inferieur on recommence 
                    
 
; generation des coins du bas:       
; generation du coin bas1       
 ADD BX, 2          ; on se positionne en bas-gauche
 MOV [BX], 200      ; on place l'angle
; generation du coin bas2
 ADD BX, 158        ; on se positionne en bas-droite
 MOV [BX], 188      ; on place l'angle      
 

; generation ligne basse du cadre 
  boucle_bas:  
 SUB BX, 2          ; on se place juste avant l'angle droit
 MOV [BX], 205      ; on affiche une bordure
 CMP BX, 3842       ; on compare a 3842 
 JA boucle_bas      ; si superieur on recommence

;  |||  fin generation du cadre   |||



;  |||  generation du point   ||| 
             
    MOV AX, 851     ; position du premier point 
    MOV BX, AX      ; on met AX dans BX
    MOV [BX], 0x1Eh ; affiche un carre blanc  
    MOV DX, 0 
    

;  |||  fin generation du point   |||  
                           
                           
                          
;  |||  gestion du deplacement   |||                          
  
  boucle2:          ; boucle de direction en bas a droite            
    ADD AX, 162             ; on se deplace de 1 vers le bas et 1 a droite
    MOV BX, AX              ; on met AX dans BX
    MOV [BX], 0xEEh         ; on colore 
     

    
    CALL Angle_BD
    
    CMP AX, 3680            ; on compare AX a 3680
    JG   boucle3            ; si derniere ligne, rebond vers haut-droit  
        CALL BD_Coll        ; on verifie la collision
    JMP boucle2             ; sinon on boucle



boucle3:            ; boucle direction en haut a droite  
    SUB AX, 158             ; on se deplace de 1 vers le haut et 1 a droite
    MOV BX, AX              ; on met AX dans BX
    MOV [BX], 0xBBh         ; on colore
    
    
    CALL Angle_HD
    
    CMP AX, 480             ; on compare AX a 480
    JB   boucle2            ; si inferieur
        CALL HD_Coll        ; on verifie la condition
    JMP boucle3             ; sinon on boucle



boucle4:            ; boucle direction en bas a gauche 
    ADD AX, 158             ; on se deplace de 1 vers le bas et 1 vers la gauche
    MOV BX, AX              ; on met AX dans BX
    MOV [BX], 0x88h         ; on colore
    
    
    CALL Angle_BG
    
    CMP AX, 3680            ; on compare AX a 3680
    JG  boucle5             ; si superieur   
        CALL BG_Coll        ; on verifie la collision
    JMP boucle4             ; sinon on boucle

boucle5:            ; boucle direction en haut a gauche
    SUB AX, 162             ; on se deplace de 1 vers le haut et 1 vers la gauche
    MOV BX, AX              ; on met AX dans BX
    MOV [BX], 0xFFh         ; on colore
    
    
    CALL Angle_HG
    
    CMP AX, 480             ; on compare AX a 480
    JB  boucle4             ; si superieur
        CALL HG_Coll        ; on verifie la condition
    JMP boucle5             ; sinon on boucle
    
;  |||  fin gestion du deplacement   ||| 


RET       

;//////////////     FIN PROGRAMME     \\\\\\\\\\\\\\
        
                    
                    