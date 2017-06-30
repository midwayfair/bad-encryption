A DIY project for my intro to CS course.

Task: Create a program that will (a) encrypt user input or (b) decrypt text that has been encrypted in such a manner. Use file input and output in accomplishing this task.

	The first task should be to come up with an encryption scheme. There are many, many schemes, and I’ve chosen a simple substitution scheme because the point of this exercise has more to do with file input and output and token processing than encryption. However, this will result in the bones of a program that could be adapted to something that resembles secure encryption.

	Some info to get us started:

	Simple substitution encryption involves replacing one character for another. Caesar cipher is an extremely well-known example of this. You simply take the letters of the alphabet, convert them to 26 numbers (A = 1, B = 2), and then add the value of a key (an arbitrary number from 1-25) to each converted letter. For instance, the word “apple” is 1 16 16 12 5, and if we shift it by 1, we get 2 17 17 12 6. If you shift any letter to a number above 26, it wraps around. (In programming languages, you can do that with the modular operator.) Then you print the resulting letter. Encrypted apple would read “bqqmf” in cypher text. This is obviously encrypted text, and I remember breaking these in 5th or 6th grade when they appeared in issues of Boy’s Life. Probably not very secure if children use it. However, Caesar cipher with a key of 26 is rumored to be slightly more secure than the other keys you could choose, for whatever reason.

	A slightly less obvious method is legionnaire's cypher, which is the same as Caesar but rotates the key based on a key word. Without getting too deep into this, you’d encrypt the keyword with Caesar cypher, then write that word’s number values over and over again above the message you want to encrypt. If we use the word apple again, you’d add 1 to the first character in your message, 16 to the second character, 16 to the third, 12 to the fourth, 5 to the fifth, and then start over again, continuing until you finish the entire message. This sounds more secure because people can’t just try a single number to shift the entire message, but you can take guesses at certain words and eventually you’d work out the keyword and then be able to decrypt the whole message. This can be done fairly quickly.

	There are truly secure methods using large prime numbers and public or private keys, which you can read about elsewhere. (Perhaps in the security injections modules …) All encryption methods have their flaws, but some encryption methods’ flaws cannot be programmatically exploited before the heat death of the universe, so if you’re interested in such things, it might be worth taking this program later and reworking it to different schemes.

	Again, this isn’t a task on encrypting things necessarily, but I am just trying to give some info on why you wouldn’t actually want to use the encryption scheme we’re using.

	What we’ll be doing is generating a random integer to use as the key, and then simply add the key to the numerical representation of each character in the message. You’ll get nice gigantic numbers, which could be positive or negative, but if you think about it for a second, you just end up with everything being in a range smaller than 128 (all the stuff you can type into a keyboard). So this might actually be less secure than Caesar cipher. Did I mention that you don’t actually want to use this?

	I. Your first task is to set up a method that generates a key and uses it to encrypt each character in a user-input string.
	Create a new method that will perform encryption, and call it something memorable.

	The key should be generated randomly, so you’ll probably want to use the Random() class; and you should generate an integer value but think very carefully about what type you need to actually hold the encrypted text if we’re adding character values to it.

	Next, instantiate your console input in the usual manner. Store the user’s input in a string so that you can reference it later.

	In previous labs, we’ve iterated over a string to print out individual characters or perform math on the characters. (Review those methods that you used if you don’t remember how to implement them.) For now, we’re doing the same thing. Just take the user’s input and echo it back to them at the console with the integers representing the encrypted characters in their string.

	That’s it for encrypting a message. So far so good?

	II. Your next task is to print the encrypted output to a file instead of the console.

	We’ve just done part of this task in previous labs. But we need to stop and think for a second about a step we haven’t gotten to yet: Eventually, we’ll have to decrypt this file, and we don’t want to have to sit there with pen and paper and work out what the key was.

	Ordinarily, keys are kept secret. Since we’re not doing anything remotely secure anyway and just demonstrating a principal, we’re going to just use the key integer that we randomly generated to name the file.

	Set up your new File() creation in the usual manner so that you generate a KEY.txt file, where KEY is the actual integer value that was generated for the key variable. Set up your PrintStream as you normally would for writing to that file, and test that now when you enter your message it does, indeed, print to the file.

	We can stop here for now as far as input goes; there will be some extra refinements we can make later.

	III. Import the file, and decrypt it using the same key.

	Make a new function that will perform your decryption.

	Since we’ve decided to be extremely unsecure about both the key and the encryption method, the decryption step is actually fairly simple; we can automate it. You can do one of two things: You can have the user input the key, and from the key have the program figure out what the corresponding file name must be, or you can ask the user for the file name and parse the key out of it. You might try implementing both and deciding which is ultimately more broadly useful were you ever to adapt this program to something more secure.

	Decryption is the reverse of the encryption process. We added a value to the characters to encrypt them, so now we must do the opposite. To get to that point, you need to read each number in the encrypted file. You can expect to read numbers from it, but you should think about what the maximum values you could read would be.

	Each time you decrypt a character, simply print it to the console.

	IV. Some small improvements.

	We now have two methods, but we need to let the user pick one or the other. Have main, or an appropriate “menu” function, determine which mode they want for our cipher, decryption or encryption. It would be best if main() handled the responsibility of reporting whether the program was successful. Also, don’t forget that the user will need to know where their file ended up and what it was called. So don’t forget to tell them! Since I had to use a void method in addition to a value-returning method, I chose to make the decryption method void and the encryption method return a status string (the file name and location if successful or an error message if not).

	I realized that I might want to encrypt a multi-line, multi-paragraph message, so I chose to implement a flag to detect when two blank lines had been entered by the user.

	Another option might be to simply take a text file and create an encrypted version using our cipher. You might try that instead of the multi-line “typewriter” method I chose as my refinement.

	A final set of improvements should be adding error checking: You need to verify that the file exists when you try to open the encrypted file, and you need to make sure that the file you create for writing the cipher text to is writeable. Failures should produce the appropriate error message.
