#Alex Painter
#Section: 5

#Java interpretation:
#int[2] divide(int dividendHigh, int dividendLow, int divisor){
#	int mask = 1;
#	while (mask != divisor){
#		mask << 1;
#		dividendLow >> 1;
#		if ((dividendHigh & 0x1) != 0){
#			dividendLow = dividendLow | 0x80000000;
#		}
#	}
#	return [dividendHigh, dividendLow];
#}


#global declarations
.globl welcome 
.globl prompt_dividend_high
.globl prompt_dividend_low
.globl prompt_divisor
.globl result_upper_32
.globl result_lower_32

#Data
.data

welcome: 
	.asciiz "This program divides two numbers"					#33

prompt_dividend_high:
	.asciiz "\n\nEnter the upper 32 bits of the dividend: "		#45

prompt_dividend_low:
	.asciiz "\n\nEnter the lower 32 bits of the dividend: "		#45

prompt_divisor:
	.asciiz "\n\nEnter a 31 bit divisor: "						#28

result_upper_32:
	.asciiz "\n\nUpper 32 bits of result: "						#29

result_lower_32:
	.asciiz "\n\nLower 32 bits of result: "	 					#29

#Text Area (i.e. instructions)
.text

main:
	#load welcome message
	ori     $v0, $0, 4
	lui     $a0, 0x1001
	syscall	

	#prompt user to enter high dividend
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x21
	syscall

	#read and store user input
	ori     $v0, $0, 5
	syscall

	#clear register and store number
	ori     $s0, $0, 0
	addu    $s0, $v0, $s0


	#prompt user to enter low dividend
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x4E
	syscall

	#read and store user input
	ori     $v0, $0, 5
	syscall

	#clear register and store number
	ori     $s1, $0, 0
	addu    $s1, $v0, $s1

	#prompt user to enter divisor
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x7A
	syscall

	#read and store user input
	ori     $v0, $0, 5
	syscall

	#clear register and store number
	ori     $s2, $0, 0
	addu    $s2, $v0, $s2

	#Logic
	#initialize register for mask
	addi	$s3, $s3, 1

loop:
	#break loop if the mask = the divisor
	beq		$s2, $s3, print
	
	#right shift the lower bits of dividend the correct number of times based on divisor value
	sll     $s3, $s3, 1
	srl 	$s1, $s1, 1
	
	#isolate LSB of upper bits of dividend
	andi	$s4, $s0, 1

	#if this bit is set, set the MSB of lower bits of dividend by anding with 0x80000000
	beq		$s4, $0, shift_upper
	ori		$s5, $0, 1
	sll     $s5, $s5, 31
	or   	$s1, $s1, $s5

shift_upper:
	#right shift the lower bits of dividend now that the LSB of upper bits is properly preserved 
	srl 	$s0, $s0, 1 
	j 		loop

print:
	#display results text to user
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0x95
	syscall

	#print result upper 32 bits
	ori     $v0, $0, 1
	add 	$a0, $s0, $0
	syscall	


	#display results text to user
	ori     $v0, $0, 4	
	lui     $a0, 0x1001
	ori     $a0, $a0, 0xB0
	syscall

	#print result upper 32 bits
	ori     $v0, $0, 1
	add 	$a0, $s1, $0
	syscall	

	# Exit (load 10 into $v0)
	ori     $v0, $0, 10
	syscall
