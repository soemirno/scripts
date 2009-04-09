#!/usr/bin/ruby
require 'digest/md5'
require 'csv'
require 'base64'

def calculate_hash(correspondentie_nr, polis_nr)
  data = "'" + correspondentie_nr + "'-'" + polis_nr +  "'"
  return Base64.encode64(Digest::MD5.digest(data)).chomp  
end

File.open('insert_hashed_data.sql', 'w') do |f| 
  
  CSV::Reader.parse(File.open('data.csv')) do |row|
    f.write("insert into polis_hash(hash) values ('" + calculate_hash(row[1], row[0]) + "');\n")
  end

end