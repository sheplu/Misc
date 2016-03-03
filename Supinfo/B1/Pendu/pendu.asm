;#fasm#          ; this code is for flat assembler

name "Jeu du Pendu"   ; output 3 name (max 8 chars for DOS compatibility)

org  100h    ; set location counter to 100h


; Set video mode (80x25. 16 colors. 8 pages.)
;mov ax, 3h
;int 10h
                                   
                                   
                                   
rand MACRO  p1, p2
    ;on prend le temps comme seed
    mov ah, 2Ch
    INT 21h
    ;on multiplie par 7
    mov ax, 7h
    mul dx
    mov bx,ax
    ;on fait un xor avec 173
    mov ax, 10101101b
    mul bx
    mov bx,ax
    ; on divise sans reste par 19
    mov bx, 13h
    mov dx, 0h
    div bx
    ;et on remultiplie par 13
    mov bx,ax
    mov ax, 7h
    mul bx

    ;ax contient le nombre pseudo-rand
    ;on récupère entre p1 et p2
    ;(X mod p2)+p1
    mov dx, 0h
    mov bx, p2
    idiv bx     ; modulo
    mov ax, dx
    add ax, p1  ; add
    lea bx, var_rdm
    mov word ptr[bx], ax
ENDM

.code
                                   
                                   
;//////////////////////////////////
;//////////////////////////////////
;//                              //
;//        Menu by Cyrbil        //
;//                              //
;//////////////////////////////////
;//////////////////////////////////


; V1.03
; Draw loading in page 0

begining:
;init variable ...
lea bx, pos
mov word ptr[bx], 0D23h
lea bx, mode_stat
mov byte ptr[bx], 0h
lea bx, comp_stat
mov byte ptr[bx], 0h
; Disable blinkin cursor
mov ch, 32
mov ah, 1
int 10h


mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
mov bx, 01001110b       ; Page number/Attribute
mov cx, loading_tail - loading  ; String length
mov dx, 0B10h           ; Start drawing coord
push cs
pop es
mov bp, loading         ; Pointer to string
int 10h                 ; Draw !!!

; Draw menu in page 1
mov cx, menu_tail - menu  ; String length
mov bp, menu               ; Change string
mov dx, 0h                  ; Change coords
mov bh, 01h                 ; Change page
int 10h                     ; draw !!!

; Change active page to 1
mov ax, 0501h
int 10h

jmp menu_loop
pos dw 0D23h   ; init menu variable, pos
mode_stat db 0h    ; init menu variable, state
comp_stat db 0h

menu_loop:
    ; Waiting for an input
    mov ch, 32
    mov ah, 1
    int 10h
    mov ah, 0
    int 16h
    ;if key stroke, choose action
    cmp ah, 39h
        je option_modif
    cmp ah, 1Ch
        je option_modif
    cmp ah, 4Bh      
        je option_modif
    cmp ah, 4Dh      
        je option_modif
    
    cmp ah, 48h
        je option_change
    cmp ah, 50h
        je option_change
        jmp menu_loop
    
    ; 0D23h - 0F29h - 1224h
    ; if we press up or down
    option_change:
        push ax
        cmp pos,0F29h;compare
            jg chg_great          ;is greater ?
            jl chg_less           ;else is less
        ; else, it's middle
        ; set mode to unfocus
        cmp comp_stat, 1h
            je menu2_2
            call comp1_draw_out
            jmp menu2_2_end
    menu2_2:
        call comp2_draw_out
    menu2_2_end:
        ; if we press up or down
        pop ax
        cmp ah, 50h
        je chg_middle_down
        cmp mode_stat, 1h
        jne chg_middle_down_2
        call mode1_draw_in
        lea bx, pos
        mov word ptr[bx], 0D23h
        jmp menu_loop
    chg_middle_down_2:
        call mode2_draw_in
        lea bx, pos
        mov word ptr[bx], 0D23h
        jmp menu_loop
    chg_middle_down:
        call start_draw_in
        lea bx, pos
        mov word ptr[bx], 1224h
        jmp menu_loop
    chg_great: ; last
        ; set start to unfocus
        call start_draw_out
        ; if we press up or down
        pop ax
        cmp ah, 50h
        je chg_last_down
        cmp comp_stat, 1h
        je chg_last_down_2
        call comp1_draw_in
        lea bx, pos
        mov word ptr[bx], 0F29h
        jmp menu_loop
    chg_last_down_2:
        call comp2_draw_in
        lea bx, pos
        mov word ptr[bx], 0F29h
        jmp menu_loop
    chg_last_down:
        cmp mode_stat, 1h
        jne chg_last_down_22
        call mode1_draw_in
        lea bx, pos
        mov word ptr[bx], 0D23h
        jmp menu_loop
    chg_last_down_22:
        call mode2_draw_in
        lea bx, pos
        mov word ptr[bx], 0D23h
        jmp menu_loop
    chg_less:  ; top
        ; set mode to unfocus
        cmp mode_stat, 1h
        jne menu1_2
        call mode1_draw_out
        jmp menu1_2_end
    menu1_2:
        call mode2_draw_out
    menu1_2_end:
        ; if we press up or down
        pop ax
        cmp ah, 50h
        jne chg_less_down
        cmp comp_stat, 1h
        je chg_less_down_2
        call comp1_draw_in
        lea bx, pos
        mov word ptr[bx], 0F29h
        jmp menu_loop
    chg_less_down_2:
        call comp2_draw_in
        lea bx, pos
        mov word ptr[bx], 0F29h
        jmp menu_loop
    chg_less_down:
        call start_draw_in
        lea bx, pos
        mov word ptr[bx], 1224h
        jmp menu_loop
    
    ; if we press a modif button
    option_modif:
        lea bx, pos
        cmp word ptr[bx],0F29h   ;compare
        jg modif_great          ;is greater ?
        jl modif_less           ;else is less
        ; middle
        cmp comp_stat, 0h
        je modif_middle_2
        call comp1_draw_in
        lea bx, comp_stat
        mov byte ptr[bx], 0h
        jmp menu_loop
    modif_middle_2:
        call comp2_draw_in
        lea bx, comp_stat
        mov byte ptr[bx], 1h
        jmp menu_loop
    modif_great: ; last
        cmp ah,1Ch
        je menu_end
        cmp ah, 39h
        je menu_end
        jmp menu_loop
    modif_less:  ; first
        cmp mode_stat, 0h
        je modif_first_2
        call mode2_draw_in
        lea bx, mode_stat
        mov byte ptr[bx], 0h
        jmp menu_loop
    modif_first_2:
        call mode1_draw_in
        lea bx, mode_stat
        mov byte ptr[bx], 1h
        jmp menu_loop

menu_end:
        
        
;//////////////////////////////////
;//////////////////////////////////
;//                              //
;//             GAME             //
;//                              //
;//////////////////////////////////
;//////////////////////////////////

mov al, 1
mov bh, 2                         ; draw on the 3rd page
mov bl, 01001110b
mov cx, game_draw_tail - offset game_draw ; calculate message size. 
mov dl, 0                          
mov dh, 0                         ; set cursor to origin
push cs
pop es
mov bp, offset game_draw  
mov ah, 13h
int 10h                           ; Draw !!!

mov cx,10
init_guess:
    lea bx, guess
    add bx, cx
    dec bx
    mov byte ptr[bx],'_'
loop init_guess
lea bx, taille
mov byte ptr[bx],0h
lea bx, found
mov byte ptr[bx],0h
lea bx, fail
mov byte ptr[bx],0h
lea bx, pendu_stat
mov byte [bx],0h
lea bx, propose
mov byte ptr[bx],0h

cmp mode_stat, 0h
    je prompt
    jmp dico
	

prompt:
    call player1_draw 
    
    ;enable blinkin cursor
    mov ah, 1
    int 10h
    ;set active page
    mov al, 2
    mov ah, 05h
    int 10h
    pop ax
    
    ;set cursor position
    mov dh, 18
    mov dl, 38
    mov ah, 2
    int 10h
    
    ;prompt for a word
    mov dx, offset buffer
    mov ah, 0ah
    int 21h
    jmp game

game:
    ;set active page
    mov al, 2
    mov ah, 05h
    int 10h
    pop ax
    
    call player2_draw
    ;display underscores
    mov bx, offset taille
    mov cl, buffer[1]
    mov byte ptr[bx], cl ;get size of word
    
    game2:
    mov al, 1
    mov bh, 2
    mov bl, 01001110b
    xor cx,cx
    mov cl, taille 
    mov dl, 27
    mov dh, 15
    push cs
    pop es
    mov bp, offset guess
    mov ah, 13h
    int 10h
    call hangman_draw

;main game
pendu_loop:
    ;display scores if prompted
    cmp comp_stat,0
    jne score_next
        call score_diplay
    score_next:
    ;move cursor
    mov dl, propose
    add dl, 37
    mov dh, 18
    mov bh, 2
    mov ah, 2
    int 10h
    mov bx, offset propose
    inc byte ptr[bx] 
    ;look for an input
    mov ah, 1
    int 21h
    ;test if it's a char
      ;si on est dans l'interval 61h-7Ah
      ;alors c'est une lettre minuscule.
    cmp al, 60h
    jle pendu_loop
    cmp al,7Bh
    jge pendu_loop
    ;if it's,
    ;compare with word
    ;init fail
    lea bx, fail
    mov byte ptr[bx],0
    ;loop sur la longueur du mot.
    xor cx,cx
    mov cl, taille
    compare_label:
        xor bx,bx
        mov bl, taille
        sub bx, cx
        ;si la lettre est deja devine, on saute
        mov dl, guess[bx]
        cmp dl,'_'
        je compare1
            lea bx, fail
            inc byte ptr[bx]
            loop compare_label ;break
            jmp compare_end
        compare1:
        ;si la lettre n'est pas egal a celle a deviner,
        mov dl, buffer[bx+2]
        cmp dl,al
        je compare2
            lea bx, fail
            inc byte ptr[bx]
            loop compare_label ;break
            jmp compare_end
        compare2:
        push bx
        add bx, offset guess
        mov byte ptr[bx], al
        pop bx
        ;affichage de la lettre,
            ;move cursor
            mov dl, 27
            add dl, bl
            mov dh, 15
            mov bh, 2
            mov ah, 2
            int 10h
            ;write char
             mov ah, 0Ah
             push cx
             mov bh, 2
             mov cx, 1
            int 10h
            pop cx 
        ;ajustage variables
        mov bx, offset found
        inc byte ptr[bx]
    loop compare_label
    compare_end:
    ;if we guess same letter's sum than word lenght
    mov bl,found
    cmp bl, taille
    je game_win ;we win
    ;if we don't find a letter
    mov bl, fail
    cmp bl,taille
    je inc_pendu
jmp pendu_loop 

inc_pendu:
    mov bx, offset pendu_stat
    inc byte ptr[bx]
    cmp pendu_stat, 8 ; on a 7 chances ...
        je pendu_loose
    cmp pendu_stat, 1
        je pendu_loose1
    cmp pendu_stat, 2
        je pendu_loose2
    cmp pendu_stat, 3
        je pendu_loose3
    cmp pendu_stat, 4
        je pendu_loose4
    cmp pendu_stat, 5
        je pendu_loose5
    cmp pendu_stat, 6
        je pendu_loose6
    cmp pendu_stat, 7
        je pendu_loose7
            
jmp pendu_loop

 
score_diplay   PROC 
    
    lea bx, found
    add byte ptr[bx],48
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov cx, 1              ; String length
    mov dl, 23
    mov dh, 18
    mov bp, bx        ; Pointer to string
    mov bh, 2
    mov bl, 01001110b      ; Page number/Attribute
    int 10h                 ; Draw !!!
    lea bx, found
    sub byte ptr[bx],48
    RET
    score_diplay   ENDP
mode1_draw_in    PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bx, 101001110b      ; Page number/Attribute
    mov cx, 12h             ; String length
    mov dx, 0D23h           ; Start drawing coord
    mov bp, mode1_in        ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    mode1_draw_in    ENDP
mode1_draw_out   PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bx, 101001110b      ; Page number/Attribute
    mov cx, 12h             ; String length
    mov dx, 0D23h           ; Start drawing coord
    mov bp, mode1_out       ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    mode1_draw_out  ENDP
mode2_draw_in    PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bx, 101001110b      ; Page number/Attribute
    mov cx, 12h             ; String length
    mov dx, 0D23h           ; Start drawing coord
    mov bp, mode2_in        ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    mode2_draw_in    ENDP
mode2_draw_out   PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bx, 101001110b      ; Page number/Attribute
    mov cx, 12h             ; String length
    mov dx, 0D23h           ; Start drawing coord
    mov bp, mode2_out       ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    mode2_draw_out   ENDP
comp1_draw_in    PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bx, 101001110b      ; Page number/Attribute
    mov cx, 5h              ; String length
    mov dx, 0F29h           ; Start drawing coord
    mov bp, comp1_in        ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    comp1_draw_in    ENDP
comp1_draw_out   PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bx, 101001110b      ; Page number/Attribute
    mov cx, 5h              ; String length
    mov dx, 0F29h           ; Start drawing coord
    mov bp, comp1_out        ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    comp1_draw_out   ENDP
comp2_draw_in    PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bx, 101001110b      ; Page number/Attribute
    mov cx, 5h              ; String length
    mov dx, 0F29h           ; Start drawing coord
    mov bp, comp2_in        ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    comp2_draw_in    ENDP
comp2_draw_out   PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bx, 101001110b      ; Page number/Attribute
    mov cx, 5h              ; String length
    mov dx, 0F29h           ; Start drawing coord
    mov bp, comp2_out       ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    comp2_draw_out   ENDP
start_draw_in    PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bx, 101001110b      ; Page number/Attribute
    mov cx, 7h              ; String length
    mov dx, 1224h           ; Start drawing coord
    mov bp, start_in        ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    start_draw_in    ENDP
start_draw_out   PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bx, 101001110b      ; Page number/Attribute
    mov cx, 7h              ; String length
    mov dx, 1224h           ; Start drawing coord
    mov bp, start_out       ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    start_draw_out   ENDP

player1_draw   PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bh, 2
    mov bl, 01001110b      ; Page number/Attribute
    mov cx, 10              ; String length
    mov dl, 27
    mov dh, 18
    mov bp, player_1        ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    player1_draw   ENDP
player2_draw   PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bh, 2
    mov bl, 01001110b      ; Page number/Attribute
    mov cx, 23              ; String length
    mov dl, 27
    mov dh, 18
    mov bp, player_2        ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    player2_draw   ENDP

hangman_draw    PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bh, 2
    mov bl, 01001110b       ; Page number/Attribute
    mov cx, 18              ; String length
    mov dl, 55              ; Start drawing coord
    mov dh, 12
    mov bp, hangman_l1      ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 13
    mov bp, hangman_l2      ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 14
    mov bp, hangman_l3      ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 15
    mov bp, hangman_l4      ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 16
    mov bp, hangman_l5      ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 17
    mov bp, hangman_l6      ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 18
    mov bp, hangman_l7      ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 19
    mov bp, hangman_l8      ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 20
    mov bp, hangman_l9      ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 21
    mov bp, hangman_l10     ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    hangman_draw    ENDP

pendu_loose1:
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bh, 2
    mov bl, 01001110b       ; Page number/Attribute
    mov cx, 1               ; String length
    mov dl, 69
    mov dh, 12              ; Start drawing coord
    mov bp, loose1_l1_draw  ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 13
    mov bp, loose1_l2_draw
    int 10h
    mov ax, 1300h
    mov dh, 14
    mov bp, loose1_l3_draw
    int 10h
    jmp pendu_loop
pendu_loose2:
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes 
    mov bh, 2
    mov bl, 01001110b      ; Page number/Attribute
    mov cx, 1              ; String length
    mov dl, 69             ; Start drawing coord
    mov dh, 15
    mov bp, loose2_draw       ; Pointer to string
    int 10h                 ; Draw !!!
    jmp pendu_loop
pendu_loose3:
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes 
    mov bh, 2
    mov bl, 01001110b      ; Page number/Attribute
    mov cx, 1              ; String length
    mov dl, 69             ; Start drawing coord
    mov dh, 16
    mov bp, loose3_l1_draw       ; Pointer to string
    int 10h                 ; Draw !!!
    mov ax, 1300h
    mov dh, 17
    mov bp, loose3_l2_draw       ; Pointer to string
    int 10h                 ; Draw !!!
    jmp pendu_loop
pendu_loose4:
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes 
    mov bh, 2
    mov bl, 01001110b      ; Page number/Attribute
    mov cx, 1              ; String length
    mov dl, 68             ; Start drawing coord
    mov dh, 16
    mov bp, loose4_draw       ; Pointer to string
    int 10h                 ; Draw !!!
    jmp pendu_loop
pendu_loose5:
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes 
    mov bh, 2
    mov bl, 01001110b      ; Page number/Attribute
    mov cx, 1              ; String length
    mov dl, 70             ; Start drawing coord
    mov dh, 16
    mov bp, loose5_draw       ; Pointer to string
    int 10h                 ; Draw !!!
    jmp pendu_loop
pendu_loose6:
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes 
    mov bh, 2
    mov bl, 01001110b      ; Page number/Attribute
    mov cx, 2              ; String length
    mov dl, 68             ; Start drawing coord
    mov dh, 18
    mov bp, loose6_draw       ; Pointer to string
    int 10h                 ; Draw !!!
    jmp pendu_loop
pendu_loose7:
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes 
    mov bh, 2
    mov bl, 01001110b      ; Page number/Attribute
    mov cx, 3              ; String length
    mov dl, 68             ; Start drawing coord
    mov dh, 18
    mov bp, loose7_draw       ; Pointer to string
    int 10h                 ; Draw !!!
    jmp pendu_loop
                                                    
                                                    
game_win:
    ;save score in a new variable
    mov cl, found                                                 
    lea bx, score
    add byte ptr[bx], cl
    ;end saving

    ;set active page
    push ax
    mov al, 3
    mov ah, 05h
    int 10h
    pop ax
    
    mov al, 1
    mov bh, 3                         ; draw on the 3rd page
    mov bl, 01001110b
    mov cx, win_tail - offset win ; calculate message size. 
    mov dl, 0                          
    mov dh, 0                         ; set cursor to origin
    push cs
    pop es
    mov bp, offset win  
    mov ah, 13h
    int 10h                           ; Draw !!!
    jmp again

                       
pendu_loose:
    ;jump to write score
    	
    ;set active page
    push ax
    mov al, 3
    mov ah, 05h
    int 10h
    pop ax
    
    mov al, 1
    mov bh, 3                         ; draw on the 3rd page
    mov bl, 01001110b
    mov cx, loose_tail - offset loose ; calculate message size. 
    mov dl, 0                          
    mov dh, 0                         ; set cursor to origin
    push cs
    pop es
    mov bp, offset loose
    mov ah, 13h
    int 10h                           ; Draw !!!
    jmp again



again:  
    again_stat db 0h    ; init menu variable, again
    jmp again_loop
    again_loop:
    ; Waiting for an input
    mov ch, 32
    mov ah, 1
    int 10h
    mov ah, 0h
    int 16h
    ;if key stroke, choose action
    cmp ah, 39h              ;space
        je end_again
    cmp ah, 1Ch              ;enter
        je end_again
    cmp ah, 4Bh              ;left
        je option_modif_again
    cmp ah, 4Dh              ;right
        je option_modif_again
    
    option_modif_again:
    cmp again_stat, 0h
        je modif_again
        call again_no_draw
        mov again_stat, 0h
        jmp again_loop
    modif_again:
        mov again_stat, 1h
        call again_yes_draw
        jmp again_loop
    
    end_again:
        cmp again_stat, 1h
            je begining
            jmp the_end

again_yes_draw   PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bh, 3
    mov bl, 01001110b      ; Page number/Attribute
    mov cx, 5h              ; String length
    mov dl, 24              ; Start drawing column
    mov dh, 22              ; Start drawing row
    mov bp, again_yes       ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    again_yes_draw   ENDP
again_no_draw   PROC
    mov ax, 1300h           ; AH = interruption 13H, AL = mode 1=string contains attributes
    mov bh, 3
    mov bl, 01001110b      ; Page number/Attribute
    mov cx, 5h              ; String length
    mov dl, 24              ; Start drawing column
    mov dh, 22              ; Start drawing row
    mov bp, again_no        ; Pointer to string
    int 10h                 ; Draw !!!
    RET
    again_no_draw   ENDP

var_line db 1 dup(0)
var_rdm dw 1 dup(5)                                                      
n db 1 dup(1)
word_err_msg db 'mot choisi', 13,10, '$'
DicoFile DB 'dico.txt', 0  
dico:
 
 
 
 
 
 
 
 
 
; choose random number
rand 1,10

; open and test if file exist
; open file
mov ah,3dh
; reading acces 
mov al,0 
lea dx, DicoFile
int 21h 
; if file doesn't exist: error
jc open_err
 
                        
 mov bx, ax  
 lea dx, Buffer
 push bx
 init_loop:
    pop bx                    
    ;read line                        
    choose:
        lea dx, Buffer    
        mov cx, 1
        mov ah, 3fh
        int 21h 
        jc read_err
        cmp Buffer, 10
    jne choose
           
    ; compteur line       
    compteur:
     push bx
     
        inc var_line
        mov cx, var_rdm  
        lea bx, var_line
        cmp byte ptr[bx],cl
jne init_loop        

;read char until word end,
;and save into buffer.
next_char:
    pop bx
    ;inc position into buffer
    inc n
    ;prepare reading
    lea dx, Buffer
    add dl, n
    mov cx, 1; one char
    mov ah, 3fh
    ;read
    int 21h
    jc read_err
    ;compare end line
    push bx
    mov bx, dx 
    cmp byte ptr[bx], 10
jne next_char

;push word length into buffer
lea bx, Buffer
mov dl, n
dec dl;
mov byte ptr[bx], dl

     
     
;   fin du traitement
; close file
mov ah, 3eh 
int 21h
jmp fin

;    error's traitment

open_err: ;
; file can't be open
lea dx, open_err_msg
jmp show_msg

read_err: ;
; file can't be read
lea dx, read_err_msg
jmp show_msg  
                 
word_err:
; word can't be read
lea dx, word_err_msg
jmp show_msg

show_msg:
mov ah,9
int 21h
jmp fin 


fin:
;set active page
mov al, 2
mov ah, 05h
int 10h
pop ax

lea bx, taille
mov al, buffer[0]
dec al
mov byte ptr[bx], al

call player2_draw
     
jmp game2
     
     
     
     
     
     
aff_score:
;   initialisation

mov ax,@data
mov ds,ax

;   open and test if file exist
; open file
mov ah,3dh
; reading acces 
mov al,0 
lea dx, Filename
int 21h 
; if file doesn't exist: error
jc open_err


;   preparing loop
; save handle
mov bx, ax 
lea dx, Buffer1

loop: ;
; read next X letter
mov cx, 2
; read file
mov ah, 3fh
int 21h  
; if reading probleme: error
jc read_err

; write on screen
mov cx, ax
mov si, ax
mov si+Buffer1, '$'
mov ah,9
int 21h
cmp cx, 2
je loop
     
     
;   fin du traitement
; close file
mov ah, 3eh 
int 21h

;    error's traitment

mov ah,4ch
int 21h 
RET


the_end:   
;score in text
mov cl, score                                                 
lea bx, text
mov byte ptr[bx], cl 
add byte ptr[bx], 48
;end  
;/////// write score in file
start:
mov ax, cs
mov dx, ax
mov es, ax
              
             
;open file 
mov ah, 3dh
mov al, 2
lea dx, filename
int 21h
jc err2
mov handle, ax
; seek: 
mov ah, 42h
mov bx, handle
mov al, 2
mov cx, 0
mov dx, 2
int 21h
; write to file: 
mov ah, 40h
mov bx, handle
mov dx, offset text
mov cx, text_size
int 21h

; close file 
mov ah, 3eh
mov bx, handle
int 21h
err2:
nop 
;////// end writing
mov ah,4ch
int 21h
RET 

msg: db "test"
msg_tail:
loading: db "Loading, we are drawing pretty good graphics ..."
loading_tail:

menu: db "###############################################################################",
	  db "#               _______   ________  __    __  _______   __    __              #",
	  db "#              /       \ /        |/  \  /  |/       \ /  |  /  |             #",
	  db "#              $$$$$$$  |$$$$$$$$/ $$  \ $$ |$$$$$$$  |$$ |  $$ |             #",
	  db "#              $$ |__$$ |$$ |__    $$$  \$$ |$$ |  $$ |$$ |  $$ |             #",
	  db "#              $$    $$/ $$    |   $$$$  $$ |$$ |  $$ |$$ |  $$ |             #",
	  db "#              $$$$$$$/  $$$$$/    $$ $$ $$ |$$ |  $$ |$$ |  $$ |             #",
	  db "#              $$ |      $$ |_____ $$ |$$$$ |$$ |__$$ |$$ \__$$ |             #",
	  db "#              $$ |      $$       |$$ | $$$ |$$    $$/ $$    $$/              #",
	  db "#              $$/       $$$$$$$$/ $$/   $$/ $$$$$$$/   $$$$$$/               #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                    Mode de Jeu:  >Joueur VS Joueur<                         #",
	  db "#                                                                             #",
	  db "#                    Comptage de points:  Oui                                 #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                    Start                                    #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                       Copyright 2011 - Credits: Cyril, Dorian, Jean, Maxime #",
	  db "###############################################################################"
menu_tail:
mode1_in:  db "  >Joueur VS IA<  "
mode1_out: db "   Joueur VS IA   "
mode2_in:  db ">Joueur VS Joueur<"
mode2_out: db " Joueur VS Joueur "
comp1_in:  db ">Oui<"
comp1_out: db " Oui "
comp2_in:  db ">Non<"
comp2_out:  db " Non "
start_in:  db ">Start<"
start_out: db " Start "

         
buffer db 11 dup(' ') ;buffer 10 characteres initialise e un espace.
guess db 11 dup('_')  ;buffer a deviner.
taille db 1 dup(0); contiendra le nombre de lettres
found db 1 dup(0); nombre de lettres trouvees
fail db 1 dup(0); si la lettre est bonne ou non.
pendu_stat db 1 dup(0) ; etat du pendu
propose db 1 dup(0) ; decalage du curseur      
;score & ecriture
score db 1 dup(0); score total depuis debut du jeu 
filename db "score.txt", 0
handle dw ?

text db ?
text_size = $ - offset text  
;/// affichage score
Buffer1 db 100 dup (?), '$'    
; message if opening error
open_err_msg db 'Erreur a l ouverture', 13, 10, '$'  
; message if reading error
read_err_msg db 'Erreur a la lecture', 13, 10, '$'
;fin score                            
                            
                            
hangman_l1: db "   ,==============",
hangman_l2: db "   ||  /          ",
hangman_l3: db "   || /           ",
hangman_l4: db "   ||/            ",
hangman_l5: db "   ||             ",
hangman_l6: db "   ||             ",
hangman_l7: db "   ||             ",
hangman_l8: db "  /||             ",
hangman_l9: db " //||             ",
hangman_l10:db "==================" 

loose1_l1_draw: db "Y",
loose1_l2_draw: db "|",
loose1_l3_draw: db "|"
loose2_draw: db "o"
loose3_l1_draw: db "|",
loose3_l2_draw: db "|"
loose4_draw: db "/"
loose5_draw: db "\"
loose6_draw: db "/|"
loose7_draw: db " |\"


win: db "################################################################################",
       db "#                                                                              #",
       db "#    __      __                                                                #",
       db "#   /  \    /  |                                                               #",
       db "#   $$  \  /$$/______   __    __                            ||                 #",
       db "#    $$  \/$$//      \ /  |  /  |                          ||||                #",
       db "#     $$  $$//$$$$$$  |$$ |  $$ |                          ||||                #",
       db "#      $$$$/ $$ |  $$ |$$ |  $$ |                          ||||                #",
       db "#       $$ | $$ \__$$ |$$ \__$$ |                          ||||                #",
       db "#       $$ | $$    $$/ $$    $$/                            ||                 #",
       db "#       $$/   $$$$$$/   $$$$$$/                             ||                 #",
       db "#                                                                              #",
       db "#    __       __   __    __       __              ||||      ||     ||||        #",
       db "#   /  |     /  | /  |  /  \     /  |             \  /     ||||    \  /        #",
       db "#   $$ | __  $$ | $$ |  $$$ \    $$ |             / /     ______    \ \        #",
       db "#   $$ |/  | $$ | $$ |  $$$$ \   $$ |            / /     |      |    \ \       #",
       db "#   $$ |$$ | $$ | $$ |  $$|$$ \  $$ |            \ \    |  o  o  |   / /       #",
       db "#   $$ |$$ | $$ | $$ |  $$| $$ \ $$ |             \ \    \      /   / /        #",
       db "#   $$ |$$ |_$$ | $$ |  $$|  $$ \$$ |              \ \    \____/   / /         #",
       db "#   $$  $$   $$ | $$ |  $$|   $$ $$ |               \ \    |  |   / /          #",
       db "#   $$$$$$$$$$$/  $$/   $$/    $$$$/                                           #",
       db "#                                                                              #",
       db "#          Recommencer :                                                        #",
       db "#                                                                              #",
	   db "################################################################################"
win_tail:

loose: db "################################################################################",
       db "#                                                    ___________.._______      #",
       db "#    __      __                                     | .__________))______|     #",
       db "#   /  \    /  |                                    | | / /      ||            #",
       db "#   $$  \  /$$/______   __    __                    | |/ /       ||.-''.       #",
       db "#    $$  \/$$//      \ /  |  /  |                   | |/         |/  _  \      #",
       db "#     $$  $$//$$$$$$  |$$ |  $$ |                   | |          ||  `/,|      #",
       db "#      $$$$/ $$ |  $$ |$$ |  $$ |                   | |          (\\`_.'       #",
       db "#       $$ | $$ \__$$ |$$ \__$$ |                   | |         .-`--'.        #",
       db "#       $$ | $$    $$/ $$    $$/                    | |        /Y . . Y\       #",
       db "#       $$/   $$$$$$/   $$$$$$/                     | |       // |   | \\      #",
       db "#                                                   | |      //  | . |  \\     #",
       db "#    __                                             | |     ')   |   |   (`    #",
       db "#   /  |                                            | |          ||'||         #",
       db "#   $$ |  ______    ______    _______   ______      | |          || ||         #",
       db "#   $$ | /      \  /      \  /       | /      \     | |          || ||         #",
       db "#   $$ |/$$$$$$  |/$$$$$$  |/$$$$$$$/ /$$$$$$  |    | |          || ||         #",
       db "#   $$ |$$ |  $$ |$$ |  $$ |$$      \ $$    $$ |    | |         / | | \        #",
       db "#   $$ |$$ \__$$ |$$ \__$$ | $$$$$$  |$$$$$$$$/     ==========|_`-' `-' |===|  #",
       db "#   $$ |$$    $$/ $$    $$/ /     $$/ $$       |    |=|=======\ \       '=|=|  #",
       db "#   $$/  $$$$$$/   $$$$$$/  $$$$$$$/   $$$$$$$/     | |        \ \        | |  #",
       db "#                                                   | |         \ \       | |  #",
       db "#          Recommencer :                             | |          `'       | |  #",
       db "#                                                                              #",
	   db "################################################################################"
loose_tail:
again_yes: db ">Oui<"
again_no: db ">Non<"

game_draw: db "###############################################################################",
	  db "#               _______   ________  __    __  _______   __    __              #",
	  db "#              /       \ /        |/  \  /  |/       \ /  |  /  |             #",
	  db "#              $$$$$$$  |$$$$$$$$/ $$  \ $$ |$$$$$$$  |$$ |  $$ |             #",
	  db "#              $$ |__$$ |$$ |__    $$$  \$$ |$$ |  $$ |$$ |  $$ |             #",
	  db "#              $$    $$/ $$    |   $$$$  $$ |$$ |  $$ |$$ |  $$ |             #",
	  db "#              $$$$$$$/  $$$$$/    $$ $$ $$ |$$ |  $$ |$$ |  $$ |             #",
	  db "#              $$ |      $$ |_____ $$ |$$$$ |$$ |__$$ |$$ \__$$ |             #",
	  db "#              $$ |      $$       |$$ | $$$ |$$    $$/ $$    $$/              #",
	  db "#              $$/       $$$$$$$$/ $$/   $$/ $$$$$$$/   $$$$$$/               #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                                                                             #",
	  db "#                       Copyright 2011 - Credits: Cyril, Dorian, Jean, Maxime #",
	  db "###############################################################################"
game_draw_tail:
player_1: db "Joueur 1 : "
player_2: db "Joueur 2 :               "
                                                   
; [SOURCE]: z:\home\cyrbil\Bureau\rdfghkjk\pendu.asm



;TODO :
;
;probleme avec le dico...
;pas moyen de placer correctement le cursor Joueur1 en 1vs1