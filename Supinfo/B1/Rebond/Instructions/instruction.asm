name "instruction"   

org  100h	
             
    MOV AX, 0
    MOV BX, 0
    MOV CX, 0
    MOV DX, 0
; placer une valeur immediate dans un registre
    MOV AX, 2       ; on place 2 dans AX
    
; placer une valeur contenue dans la memoire dans un registre different
    MOV [05], 30    ; on met 30 en memoire
    MOV BX, [05]    ; on place 30 dans BX
    
; additionner deux registres
    ADD AX, BX      ; on ajoute BX a AX
    
; placer en memoire le resultat
    MOV [05], AX    ; on met AX en memoire       

RET