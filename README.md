## Project overview
The high-level goal of the project is to provide ported implementation of fixed-length library from Scala 2 into Scala 3. By implementing the Encoder and Decoder that
can convert between Scala data types and String, extra high-level utilities can be further provided to fulfill tasks such as 
- output randomly generated data into the fixed-width formtted files with following given formatting directives
- parse fixed-width formatted files into Scala data structures, and further output to another data file (e.g. CSV) if required