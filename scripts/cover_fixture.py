"""Small, deterministic synthetic PNG for isolated cover approval tests."""
import base64
import struct
import zlib

def png_bytes(width=1200,height=600):
    def chunk(kind,data):
        return struct.pack('!I',len(data))+kind+data+struct.pack('!I',zlib.crc32(kind+data)&0xffffffff)
    rows=(b'\x00'+b'\xb6\x39\x08'*width)*height
    return b'\x89PNG\r\n\x1a\n'+chunk(b'IHDR',struct.pack('!IIBBBBB',width,height,8,2,0,0,0))+chunk(b'IDAT',zlib.compress(rows))+chunk(b'IEND',b'')

def png_data_url(width=1200,height=600):
    return 'data:image/png;base64,'+base64.b64encode(png_bytes(width,height)).decode()
