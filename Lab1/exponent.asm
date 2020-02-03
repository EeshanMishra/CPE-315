#Alex Painter
#Section: 5

#Java interpretation:
#int exponent(int x, int y){
#	int result = 0;
#	int temp_base = x;
#	for(int m=0; m<y; m++){
#		for (int n=0; n<x; n++){
#			result += temp_base;
#		}	
#		temp_base = result	
#	}
#	return result;
#}

#global declarations
.globl welcome 
.globl prompt_x
.globl prompt_y
.globl result

#Data
.data

welcome: 
	.asciiz "This program returns x^y"		#24

prompt_x:
	.asciiz "\n\nEnter the base (x): "		#24

prompt_y:
	.asciiz "\nEnter the power (y): "		#25

result_lower_32:
	.asciiz "\nResult: "	 				#12

#Text Area (i.e. instructions)
.text

main:
	#load welcome message
	ori     $v0, $0, 4
	lui     $a0, 0x1001
	syscall	

	#prompt user to enter base
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x19
	syscall

	#read and store user input
	ori     $v0, $0, 5
	syscall

	#clear register and store number
	ori     $s0, $0, 0
	addu    $s0, $v0, $s0

	#prompt user to enter power
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x30
	syscall

	#read and store user input
	ori     $v0, $0, 5
	syscall

	#clear register and store number
	ori     $s1, $0, 0
	addu    $s1, $v0, $s1

	#initialize counters, temp_base, and result
	ori		$s2, $0, 1				#m
	ori		$s3, $0, 0				#n
	ori		$s4, $s0, 0				#temp_base
	ori		$s5, $0, 1				#result

	beq 	$s1, $0, exit

	#logic
outer_loop:
	#outer loop is used to properly set temp base
	ori		$s5, $0, 0
	addiu	$s2, $s2, 1

inner_loop:
	#inner loop is used to add temp base to itself the proper number of times
	addiu	$s3, $s3, 1
	addu    $s5, $s4, $s5
	bne 	$s3, $s0, inner_loop 
	
	#set temp base to the most previous inner loop incrementation
	ori		$s4, $s5, 0
	ori		$s3, $0, 0
	beq 	$s2, $s1, exit
	j 		outer_loop

exit:
	#display results text to user
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x47
	syscall

	#print result upper 32 bits
	ori     $v0, $0, 1
	add 	$a0, $s5, $0
	syscall	

	# Exit (load 10 into $v0)
	ori     $v0, $0, 10
	syscall

