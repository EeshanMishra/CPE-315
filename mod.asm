#Alex Painter
#Section: 5

#Java interpretation:
#int mod(int num, int div){
#	int temp = div - 1;
#	return temp & num;
#}

#global declarations
.globl welcome
.globl prompt_num
.globl prompt_div
.globl result

#Data
.data

welcome: 
	.asciiz "This program performs a modulus operation\n\n"

prompt_num:
	.asciiz "Enter an integer (number): "

prompt_div:
	.asciiz "\nEnter an integer (divisor): "

result:
	.asciiz "\nModulus: "
	 
#Text Area (i.e. instructions)
.text

main:
	#load welcome message
	ori     $v0, $0, 4
	lui     $a0, 0x1001
	syscall	
	
	#prompt user to enter number
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x2C
	syscall

	#read and store user input
	ori     $v0, $0, 5
	syscall

	#clear register for number and divisor
	ori     $s0, $0, 0	
	ori     $s1, $0, 0

	# Store in register s0
	addu    $s0, $v0, $s0
		
	#prompt user to enter divisor
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x48
	syscall

	#read and store user input
	ori     $v0, $0, 5
	syscall

	# Store in register s1
	addu    $s1, $v0, $s1

	#logic: subtract 1 from divisor, & with the dividend
	subu	$s1, $s1, 1
	and 	$s0, $s0, $s1

	#display results text to user
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x66
	syscall

	#print result
	ori     $v0, $0, 1
	add 	$a0, $s0, $0
	syscall	

	# Exit (load 10 into $v0)
	ori     $v0, $0, 10
	syscall
