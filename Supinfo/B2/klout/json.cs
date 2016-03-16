using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Runtime.Serialization.Json;

namespace TpSupinfo
{
    public static class Json
    {

        /// <summary>
        /// Serializes an object to a UTF-8 encoded JSON string.
        /// </summary>
        /// <param name="obj">object to serialize</param>
        /// <returns>JSON string result</returns>

        public static string Serialize(object obj)
        {
            // Serialize to a memory stream....
            MemoryStream ms = new MemoryStream();

            // Serialize to memory stream with DataContractJsonSerializer
            DataContractJsonSerializer s = new DataContractJsonSerializer(obj.GetType());

            s.WriteObject(ms, obj);
            byte[] json = ms.ToArray();
            ms.Close();

            // return utf8 encoded json string
            return Encoding.UTF8.GetString(json, 0, json.Length);

        }

        /// <summary>
        /// Deserializes an object from a UTF-8 encoded JSON string.
        /// </summary>
        /// <typeparam name="T">type of object to deserialize as</typeparam>
        /// <param name="json">UTF-8 encoded JSON string</param>
        /// <returns>deserialized object</returns>

        public static T Deserialize<T>(string json)
        {
            T result = default(T);

            // load json into memorystream and deserialize
            MemoryStream ms = new MemoryStream(Encoding.UTF8.GetBytes(json));
            DataContractJsonSerializer s = new DataContractJsonSerializer(typeof(T));
            result = (T)s.ReadObject(ms);
            ms.Close();

            return result;

        }

    } 
}
