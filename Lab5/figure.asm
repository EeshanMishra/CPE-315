#initialize stack and coordinate pointer
addi $sp, $0, 4095
add $s0, $0, $0 			#s0: coordinate pointer

#main program
addi $a0, $0, 30
addi $a1, $0, 100
addi $a2, $0, 20
jal circle
addi $a0, $0, 30
addi $a1, $0, 80
addi $a2, $0, 30
addi $a3, $0, 30
jal line
addi $a0, $0, 20
addi $a1, $0, 1
addi $a2, $0, 30
addi $a3, $0, 30
jal line
addi $a0, $0, 40
addi $a1, $0, 1
addi $a2, $0, 30
addi $a3, $0, 30
jal line
addi $a0, $0, 15
addi $a1, $0, 60
addi $a2, $0, 30
addi $a3, $0, 50
jal line
addi $a0, $0, 30
addi $a1, $0, 50
addi $a2, $0, 45
addi $a3, $0, 60
jal line
addi $a0, $0, 24
addi $a1, $0, 105
addi $a2, $0, 3
jal circle
addi $a0, $0, 36
addi $a1, $0, 105
addi $a2, $0, 3
jal circle
addi $a0, $0, 25
addi $a1, $0, 90
addi $a2, $0, 35
addi $a3, $0, 90
jal line
addi $a0, $0, 25
addi $a1, $0, 90
addi $a2, $0, 20
addi $a3, $0, 95
jal line
addi $a0, $0, 35
addi $a1, $0, 90
addi $a2, $0, 40
addi $a3, $0, 95
jal line
j end


#---------------------------------LINE FN START-----------------------------------------------
line:			slt $t0, $a0, $a2		 
				beq $t0, $0, greaterThanX
				sub $t2, $a2, $a0
				j compareY

greaterThanX:	sub $t2, $a0, $a2

compareY:		slt $t1, $a1, $a3
				beq $t1, $0, greaterThanY
				sub $t3, $a3, $a1
				j compareAbs

greaterThanY:	sub $t3, $a1, $a3

compareAbs:		add $s1, $0, $0				#s1: ST
				slt $t5, $t2, $t3
				beq $t5, $0, xSwap
				addi $s1, $0, 1	
				add $t6, $0, $a0
				add $a0, $0, $a1
				add $a1, $0, $t6
				#swap x1, y1
				add $t7, $0, $a2
				add $a2, $0, $a3
				add $a3, $0, $t7  

#TEMP REGISTER RESET
xSwap:			slt $t0, $a0, $a2			#set if x0<x1
				bne $t0, $0, deltaCalc 	
				add $t1, $0, $a0
				add $a0, $0, $a2
				add $a2, $0, $t1
				#swap x1, y1
				add $t2, $0, $a1
				add $a1, $0, $a3
				add $a3, $0, $t2

deltaCalc:		sub $s2, $a2, $a0			#s2: delta X
				slt $t3, $a1, $a3			#set if y1>y0
				beq $t3, $0, flipY
				sub $s3, $a3, $a1			#s3: delta Y
				j errorCalc

flipY:			sub $s3, $a1, $a3	

errorCalc:		add $s4, $0, $0				#s4: error	
				add $s5, $0, $a1			#s5: y				
				beq $t3, $0, yStepNeg		
				addi $s6, $0, 1				#s6: y step					 			
				j storeArgs

yStepNeg:		addi $s6, $0, -1

#TEMP REGISTER RESET
storeArgs: 		addi $sp, $sp, -5
				sw $a0, 0($sp)
				sw $a1, 1($sp)
				sw $a2, 2($sp)
				sw $a3, 3($sp)
				sw $ra, 4($sp)

				add $t0, $0, $a0			#counter, starts at lower limit
				addi $t3, $a2, 1			#count to x1+1
loop:			beq $s1, $0, plotXY			#branch if ST==0
				
plotYX:			add $a0, $0, $s5			#arg1: y
				add $a1, $0, $t0			#arg2: x
				jal plot
				j errorHandle

plotXY:			add $a0, $0, $t0			#arg1: x
				add $a1, $0, $s5			#arg2: y
				jal plot							 				

errorHandle:	add $s4, $s4, $s3
				add $t1, $s4, $s4			#error * 2
				slt $t2, $t1, $s2
				bne $t2, $0, updateCtr
				add $s5, $s5, $s6
				sub $s4, $s4, $s2

updateCtr:		addi $t0, $t0, 1			#70
				bne $t0, $t3, loop

				add $s1, $0, $0
				add $s2, $0, $0
				add $s3, $0, $0
				add $s4, $0, $0
				add $s5, $0, $0
				add $s6, $0, $0
				add $t0, $0, $0
				add $t1, $0, $0
				add $t2, $0, $0
				add $t3, $0, $0
				add $t4, $0, $0
				add $t5, $0, $0
				add $t6, $0, $0
				add $t7, $0, $0
				lw $ra, 4($sp)
				jr $ra
			
#-------------------------------------LINE FN END-------------------------------------------


#-------------------------------------CIRCLE FN START---------------------------------------
circle:			sw $ra, 0($sp)
				add $s1, $0, $0				#s1: x
				add $s2, $0, $a2            #s2: y
				addi $s3, $0, 3				#s3: g
				sub $s3, $s3, $a2
				sub $s3, $s3, $a2
				addi $s4, $0, 10			#s4: diagonalInc
				sub $s4, $s4, $a2
				sub $s4, $s4, $a2
				sub $s4, $s4, $a2
				sub $s4, $s4, $a2
				addi $s5, $0, 6				#s5: rightInc

				add $t0, $0, $a0
				add $t1, $0, $a1

circleLoop:		add $a0, $t0, $s1
				add $a1, $t1, $s2
				jal plot					#xc+x, yc+y
               	add $a0, $t0, $s1
				sub $a1, $t1, $s2
				jal plot					#xc+x, yc-y
				sub $a0, $t0, $s1			
				add $a1, $t1, $s2
				jal plot					#xc-x, yc+y
				sub $a0, $t0, $s1
				sub $a1, $t1, $s2
				jal plot					#xc-x, yc-y
				add $a0, $t0, $s2
				add $a1, $t1, $s1
				jal plot					#xc+y, yc+x
               	add $a0, $t0, $s2
				sub $a1, $t1, $s1
				jal plot					#xc+y, yc-x
				sub $a0, $t0, $s2			
				add $a1, $t1, $s1
				jal plot					#xc-y, yc+x
				sub $a0, $t0, $s2
				sub $a1, $t1, $s1
				jal plot					#xc-y, yc-x

				slt $t2, $s3, $0
				bne $t2, $0, elseBlock
				add $s3, $s3, $s4
				addi $s4, $s4, 8
				addi $s2, $s2, -1
				j incrementCtr

elseBlock:		add $s3, $s3, $s5
				addi $s4, $s4, 4

incrementCtr:	addi $s5, $s5, 4
				addi $s1, $s1, 1
				slt $t3, $s2, $s1
				beq $t3, $0, circleLoop

				add $s1, $0, $0
				add $s2, $0, $0
				add $s3, $0, $0
				add $s4, $0, $0
				add $s5, $0, $0
				add $t0, $0, $0
				add $t1, $0, $0
				add $t2, $0, $0
				add $t3, $0, $0
				lw $ra, 0($sp)
				jr $ra
#-------------------------------------CIRCLE FN END-----------------------------------------
plot:			sw $a0, 0($s0)
				sw $a1, 1($s0)
				addi $s0, $s0, 2		#increment coordinate pointer 
				jr $ra

end: 			add $0, $0, $0
